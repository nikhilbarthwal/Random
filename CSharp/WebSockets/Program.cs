using System.Net.WebSockets;
using System.Text;

namespace WebSockets
{
    class SynchronousWebSocketClient
    {
        private ClientWebSocket  socket;

        public SynchronousWebSocketClient()
        {
            socket = new ClientWebSocket();
        }

        public void Connect(string serverUri)
        {
            socket.ConnectAsync(new Uri(serverUri), CancellationToken.None).Wait();

            // Start listening for messages and errors
            Task.Run(() => ReceiveWebSocketMessages());
        }

        public void Send(string message)
        {
            if (socket.State == WebSocketState.Open)
            {
                byte[] buffer = Encoding.UTF8.GetBytes(message);
                socket.SendAsync(new ArraySegment<byte>(buffer),
                    WebSocketMessageType.Text, true, CancellationToken.None).Wait();
            }
        }

        public string Receive()
        {
            byte[] buffer = new byte[1024];
            WebSocketReceiveResult result = socket
                .ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None)
                .Result;

            if (result.MessageType == WebSocketMessageType.Text)
            {
                return Encoding.UTF8.GetString(buffer, 0, result.Count);
            }

            return ""; // Should not happen!
        }

        private void ReceiveWebSocketMessages()
        {
            try
            {
                while (socket.State == WebSocketState.Open)
                {
                    string receivedMessage = Receive();
                    Console.WriteLine($"Received message: {receivedMessage}");

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in WebSocket communication: {ex.Message}");
            }
        }

        public void Close()
        {
            if (socket.State == WebSocketState.Open)
            {
                socket.CloseAsync(WebSocketCloseStatus.NormalClosure,
                    "Closing WebSocket", CancellationToken.None).Wait();
            }
        }

        public static void Main(string[] args)
        {
            string serverUri = "wss://api.gemini.com/v1/marketdata/BTCUSD";

            SynchronousWebSocketClient webSocketClient =
                new SynchronousWebSocketClient();
            webSocketClient.Connect(serverUri);

            // Send a test message
            webSocketClient.Send("Hello, WebSocket!");

            // Receive messages
            Console.WriteLine("Press Enter to receive messages.");
            Console.ReadLine();
            webSocketClient.Receive();

            // Close the connection
            webSocketClient.Close();
        }
    }
}
