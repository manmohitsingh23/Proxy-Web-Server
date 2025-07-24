<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Proxy Web Server - Java HTTP/HTTPS Proxy</title>
</head>
<body style="font-family: Arial, sans-serif; max-width: 900px; margin: auto; padding: 20px; line-height: 1.6;">

  <h1>Proxy Web Server</h1>
  <p><a href="https://github.com/manmohitsingh23/Proxy-Web-Server" target="_blank">GitHub Repository</a></p>

  <p>This is a simple Proxy Web Server implemented in Java. It listens on a specific port and forwards HTTP/HTTPS requests from clients to the appropriate web servers. It also blocks access to specified websites using a blacklist. Both HTTP and HTTPS protocols are supported.</p>

  <h2>✨ Features</h2>
  <ul>
    <li>Supports both HTTP and HTTPS requests</li>
    <li>Blocks specific websites using a blacklist</li>
    <li>Handles multiple clients concurrently using a thread pool</li>
    <li>Customizable blocked list via <code>blockedSites.txt</code></li>
  </ul>

  <h2>🛠 How It Works</h2>
  <ul>
    <li>Server listens on a port (default: <code>8080</code>)</li>
    <li>If the requested site is in <code>blockedSites.txt</code>, it returns a 403 Forbidden</li>
    <li>Otherwise, the request is forwarded to the destination server</li>
    <li>HTTPS is tunneled using a TCP connection</li>
  </ul>

  <h2>📦 Prerequisites</h2>
  <ul>
    <li>Java 11 or higher</li>
    <li>A text file named <code>blockedSites.txt</code> with one blocked site per line</li>
  </ul>

  <h2>🚀 Setup</h2>
  <ol>
    <li>
      <strong>Clone the Repository</strong>
      <pre><code>git clone https://github.com/manmohitsingh23/Proxy-Web-Server.git
cd Proxy-Web-Server</code></pre>
    </li>
    <li>
      <strong>Create <code>blockedSites.txt</code></strong>
      <pre><code>example.com
youtube.com</code></pre>
    </li>
    <li>
      <strong>Compile and Run</strong>
      <pre><code>javac ProxyServer.java ClientHandler.java
java ProxyServer</code></pre>
    </li>
    <li>
      <strong>Test</strong>
      <p>Set your browser or tool to use <code>localhost:8080</code> as the proxy.</p>
    </li>
  </ol>

  <h2>🔒 Blocked Site Response</h2>
  <pre><code>HTTP/1.1 403 Forbidden
Content-Type: text/html

&lt;html&gt;&lt;body&gt;&lt;h1&gt;403 Forbidden&lt;/h1&gt;&lt;p&gt;This website is blocked by the proxy server.&lt;/p&gt;&lt;/body&gt;&lt;/html&gt;</code></pre>

  <h2>🧠 Code Structure</h2>
  <ul>
    <li><strong>ProxyServer.java:</strong> Starts the server and accepts client connections</li>
    <li><strong>ClientHandler.java:</strong> Processes individual client requests</li>
    <li><strong>blockedSites.txt:</strong> List of domains to block</li>
  </ul>

  <h2>🔄 Customization</h2>
  <ul>
    <li>Edit <code>PORT</code> in <code>ProxyServer.java</code> to change the listening port</li>
    <li>Edit <code>blockedSites.txt</code> to update the blacklist</li>
  </ul>

  <h2>📄 License & Acknowledgement</h2>
  <p><strong>Copyright © 2025 Manmohit Singh</strong></p>
  <p>
    <strong>Proper attribution to the original author (Manmohit Singh) must be given in any public use, project integration, or redistribution.</strong>
  </p>
  <p>This project is licensed under the <a href="https://opensource.org/licenses/MIT" target="_blank">MIT License</a>.</p>

  <h2>👨‍💻 Made With ❤️ by Manmohit Singh</h2>
  <p>Crafted with clean code, strong coffee ☕, and lots of debugging 🐞.</p>
  <p style="text-align:center; font-style: italic;">"Eat. Sleep. Code. Repeat."</p>

</body>
</html>

