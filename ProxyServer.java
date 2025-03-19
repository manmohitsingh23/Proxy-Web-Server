import java.io.*;
import java.net.*;
import java.net.http.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyServer {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 52;
    private static final Set<String> blockedSites = new HashSet<>();

    public static void main(String[] args) {
        loadBlockedSites("blockedSites.txt");

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Proxy Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void loadBlockedSites(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                blockedSites.add(line.trim().toLowerCase());
            }
            System.out.println("Blocked sites loaded: " + blockedSites);
        } catch (IOException e) {
            System.err.println("Error loading blocked sites: " + e.getMessage());
        }
    }

    public static boolean isBlocked(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false; // Avoid NullPointerException
        }

        url = url.toLowerCase();
        for (String blocked : blockedSites) {
            if (url.contains(blocked)) {
                return true;
            }
        }
        return false;
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String requestLine = in.readLine();
            if (requestLine == null) return;

            System.out.println("Received request: " + requestLine);

            String[] tokens = requestLine.split(" ");
            if (tokens.length < 3) return;

            String method = tokens[0];
            String url = tokens[1];

            // Extract host from URL
            String host = getHostFromURL(url);
            if (host == null) {
                System.err.println("⚠️ Warning: Could not extract host from " + url);
                return;
            }

            // Check if the requested site is blocked
            if (ProxyServer.isBlocked(host)) {
                sendBlockedResponse(out);
                return;
            }

            if (method.equalsIgnoreCase("CONNECT")) {
                handleHTTPSRequest(host, out);
            } else {
                handleHTTPRequest(url, out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getHostFromURL(String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        try {
            if (url.contains(":") && !url.startsWith("http")) {
                return url.split(":")[0]; // Extract the domain part
            }

            URI uri = new URI(url);
            return uri.getHost();
        } catch (Exception e) {
            System.err.println("⚠️ Error parsing URL: " + url);
            return null;
        }
    }

    private void sendBlockedResponse(PrintWriter out) {
        out.println("HTTP/1.1 403 Forbidden");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body><h1>403 Forbidden</h1><p>This website is blocked by the proxy server.</p></body></html>");
        out.flush();
        System.out.println("Blocked access to a website.");
    }

    private void handleHTTPSRequest(String host, PrintWriter out) {
        try {
            String[] hostPort = host.split(":");
            String hostname = hostPort[0];
            int port = (hostPort.length > 1) ? Integer.parseInt(hostPort[1]) : 443;

            Socket serverSocket = new Socket(hostname, port);
            out.println("HTTP/1.1 200 Connection Established");
            out.println();
            out.flush();

            forwardData(clientSocket, serverSocket);
        } catch (IOException e) {
            System.err.println("❌ Error handling HTTPS request for: " + host);
            e.printStackTrace();
        }
    }

    private void handleHTTPRequest(String url, PrintWriter out) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            out.println("HTTP/1.1 " + response.statusCode());
            out.println("Content-Type: text/html");
            out.println("Content-Length: " + response.body().length());
            out.println();
            out.println(response.body());

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Error handling HTTP request for: " + url);
            e.printStackTrace();
        }
    }

    private void forwardData(Socket client, Socket server) {
        try {
            InputStream clientInput = client.getInputStream();
            OutputStream clientOutput = client.getOutputStream();
            InputStream serverInput = server.getInputStream();
            OutputStream serverOutput = server.getOutputStream();

            Thread clientToServer = new Thread(() -> streamData(clientInput, serverOutput));
            Thread serverToClient = new Thread(() -> streamData(serverInput, clientOutput));

            clientToServer.start();
            serverToClient.start();

            clientToServer.join();
            serverToClient.join();

            client.close();
            server.close();
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Error forwarding data");
            e.printStackTrace();
        }
    }

    private void streamData(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("❌ Error streaming data");
            e.printStackTrace();
        }
    }
}
