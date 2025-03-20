<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<h1>Proxy Web Server</h1>

<p>This is a simple Proxy Web Server implemented in Java that listens on a specific port and forwards HTTP/HTTPS requests from clients to the appropriate web servers. The proxy server can block access to specified websites by checking against a list of blocked sites. It supports both HTTP and HTTPS protocols.</p>

<h2>Features</h2>
<ul>
    <li>Handles both HTTP and HTTPS requests.</li>
    <li>Blocks access to specific websites based on a blacklist.</li>
    <li>Utilizes a thread pool to handle multiple client requests concurrently.</li>
    <li>Configurable blocked sites via a text file (<code>blockedSites.txt</code>).</li>
</ul>

<h2>How It Works</h2>
<p>The server listens on a specific port (default: 8080).</p>
<p>When a client connects, the server reads the HTTP request and extracts the URL.</p>
<p>If the URL is in the list of blocked sites (<code>blockedSites.txt</code>), the server returns a 403 Forbidden response.</p>
<p>If the URL is not blocked, the server forwards the request to the target web server.</p>
<ul>
    <li>For HTTP requests, the server forwards the request to the destination and returns the server's response.</li>
    <li>For HTTPS requests, the server establishes a TCP connection to the target and forwards data between the client and server.</li>
</ul>
<p>The proxy server uses a fixed thread pool to handle multiple client connections simultaneously.</p>

<h2>Prerequisites</h2>
<ul>
    <li>Java 11 or later is required to run this server.</li>
    <li>The <code>blockedSites.txt</code> file must be created to specify the websites you want to block.</li>
</ul>

<h2>Setup and Installation</h2>
<ol>
    <li><strong>Clone the Repository</strong>
        <pre><code>git clone https://github.com/yourusername/Proxy-Web-Server.git</code></pre>
        <pre><code>cd Proxy-Web-Server</code></pre>
    </li>
    <li><strong>Create <code>blockedSites.txt</code></strong>
        <p>The proxy uses a text file to load a list of blocked websites. Each line in this file should contain the domain name (or part of the domain) of a site you wish to block.</p>
        <pre><code>example.com</code></pre>
        <pre><code>example.net</code></pre>
    </li>
    <li><strong>Compile the Java Code</strong>
        <pre><code>javac ProxyServer.java</code></pre>
    </li>
    <li><strong>Run the Proxy Server</strong>
        <pre><code>java ProxyServer</code></pre>
        <p>This will start the server on port 8080. You can configure the port by modifying the <code>PORT</code> constant in the <code>ProxyServer.java</code> file.</p>
    </li>
    <li><strong>Test the Proxy Server</strong>
        <p>You can test the proxy server by configuring your web browser or HTTP client to use <code>localhost:8080</code> as the proxy. If you try to access any blocked sites, you will receive a 403 Forbidden response.</p>
    </li>
</ol>

<h3>Example of Blocked Response</h3>
<p>When you try to access a blocked site, the proxy will return the following HTML response:</p>
<pre><code>HTTP/1.1 403 Forbidden
Content-Type: text/html

&lt;html&gt;&lt;body&gt;&lt;h1&gt;403 Forbidden&lt;/h1&gt;&lt;p&gt;This website is blocked by the proxy server.&lt;/p&gt;&lt;/body&gt;&lt;/html&gt;</code></pre>

<h2>Code Explanation</h2>
<ul>
    <li><strong>ProxyServer.java:</strong> The main class of the proxy server that listens on a port and handles incoming requests.</li>
    <li><strong>loadBlockedSites(filename):</strong> Loads a list of blocked sites from a specified file.</li>
    <li><strong>isBlocked(url):</strong> Checks if the given URL is in the blocked sites list.</li>
    <li><strong>ClientHandler:</strong> A runnable class that handles client requests and forwards data to/from the web server.</li>
</ul>

<h2>How the Proxy Server Handles Requests</h2>
<ul>
    <li><strong>HTTP Request:</strong> The server sends an HTTP GET request to the target server and forwards the response to the client.</li>
    <li><strong>HTTPS Request:</strong> The server establishes a TCP connection with the target server (on port 443 for HTTPS), and forwards the data in both directions between the client and the server.</li>
</ul>

<h2>Threading</h2>
<p>The proxy uses a fixed-size thread pool (<code>ExecutorService</code>) to handle multiple requests simultaneously. Each incoming connection is handled by a new <code>ClientHandler</code> thread.</p>

<h2>Customization</h2>
<ul>
    <li>To change the proxy port, modify the <code>PORT</code> constant in the <code>ProxyServer.java</code> file.</li>
    <li>To modify the list of blocked sites, simply edit the <code>blockedSites.txt</code> file.</li>
</ul>

<h2>Contributing</h2>
<p>Feel free to submit pull requests or open issues for bugs or new features. Contributions are welcome!</p>

<h2>License</h2>
<p>This project is licensed under the MIT License - see the <code>LICENSE</code> file for details.</p>

</body>
</html>
