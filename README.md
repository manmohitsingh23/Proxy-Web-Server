Proxy Web Server
This is a simple Proxy Web Server implemented in Java that listens on a specific port and forwards HTTP/HTTPS requests from clients to the appropriate web servers. The proxy server can block access to specified websites by checking against a list of blocked sites. It supports both HTTP and HTTPS protocols.

Features
Handles both HTTP and HTTPS requests.
Blocks access to specific websites based on a blacklist.
Utilizes a thread pool to handle multiple client requests concurrently.
Configurable blocked sites via a text file (blockedSites.txt).
How It Works
The server listens on a specific port (default: 8080).
When a client connects, the server reads the HTTP request and extracts the URL.
If the URL is in the list of blocked sites (blockedSites.txt), the server returns a 403 Forbidden response.
If the URL is not blocked, the server forwards the request to the target web server.
For HTTP requests, the server forwards the request to the destination and returns the server's response.
For HTTPS requests, the server establishes a TCP connection to the target and forwards data between the client and server.
The proxy server uses a fixed thread pool to handle multiple client connections simultaneously.
Prerequisites
Java 11 or later is required to run this server.
The blockedSites.txt file must be created to specify the websites you want to block.
Setup and Installation
1. Clone the Repository
git clone https://github.com/yourusername/proxy-web-server.git
cd proxy-web-server
2. Create blockedSites.txt
The proxy uses a text file to load a list of blocked websites. Each line in this file should contain the domain name (or part of the domain) of a site you wish to block.

Example blockedSites.txt:

example.com
example.net
3. Compile the Java Code
javac ProxyServer.java
4. Run the Proxy Server
java ProxyServer
This will start the server on port 8080. You can configure the port by modifying the PORT constant in the ProxyServer.java file.

5. Test the Proxy Server
You can test the proxy server by configuring your web browser or HTTP client to use localhost:8080 as the proxy. If you try to access any blocked sites, you will receive a 403 Forbidden response.

Example of Blocked Response
When you try to access a blocked site, the proxy will return the following HTML response:

HTTP/1.1 403 Forbidden
Content-Type: text/html

<html><body><h1>403 Forbidden</h1><p>This website is blocked by the proxy server.</p></body></html>
Code Explanation
ProxyServer.java: The main class of the proxy server that listens on a port and handles incoming requests.
loadBlockedSites(filename): Loads a list of blocked sites from a specified file.
isBlocked(url): Checks if the given URL is in the blocked sites list.
ClientHandler: A runnable class that handles client requests and forwards data to/from the web server.
How the Proxy Server Handles Requests
HTTP Request: The server sends an HTTP GET request to the target server and forwards the response to the client.
HTTPS Request: The server establishes a TCP connection with the target server (on port 443 for HTTPS), and forwards the data in both directions between the client and the server.
Threading
The proxy uses a fixed-size thread pool (ExecutorService) to handle multiple requests simultaneously. Each incoming connection is handled by a new ClientHandler thread.

Customization
To change the proxy port, modify the PORT constant in the ProxyServer.java file.
To modify the list of blocked sites, simply edit the blockedSites.txt file.

Contributing
Feel free to submit pull requests or open issues for bugs or new features. Contributions are welcome!

License
This project is licensed under the MIT License - see the LICENSE file for details.
