#include <iostream>
#include <boost/beast.hpp>
#include <boost/beast/ssl.hpp>
#include <boost/beast/websocket.hpp>
#include <boost/asio/connect.hpp>
#include <boost/asio/ip/tcp.hpp>
#include <boost/asio/ssl/stream.hpp>



namespace beast = boost::beast;         // from <boost/beast.hpp>
namespace http = beast::http;           // from <boost/beast/http.hpp>
namespace websocket = beast::websocket; // from <boost/beast/websocket.hpp>
namespace net = boost::asio;            // from <boost/asio.hpp>
using tcp = boost::asio::ip::tcp;       // from <boost/asio/ip/tcp.hpp>
namespace ssl = boost::asio::ssl;            // from <boost/asio.hpp>
namespace net = boost::asio;        // from <boost/asio.hpp>
using tcp = net::ip::tcp;           // from <boost/asio/ip/tcp.hpp>


namespace ssl = boost::asio::ssl; // from <boost/asio/ssl.hpp>
namespace http = boost::beast::http;    // from <boost/beast/http.hpp>
namespace beast = boost::beast;     // from <boost/beast.hpp>


#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>


inline void load_root_certificates(ssl::context& ctx, boost::system::error_code& ec)
{
	HCERTSTORE hstore = CertOpenSystemStore(NULL, L"ROOT");
	if (!hstore) return;

	char* data = NULL;
	PCCERT_CONTEXT pcert_context = NULL;

	do
	{
		pcert_context = CertEnumCertificatesInStore(hstore, pcert_context);
		if (!pcert_context) break;

		X509* x509 = d2i_X509(NULL, const_cast<const BYTE**>(&pcert_context->pbCertEncoded), pcert_context->cbCertEncoded);

		BIO* bio = BIO_new(BIO_s_mem());
		if (PEM_write_bio_X509(bio, x509))
		{
			const long int len = BIO_get_mem_data(bio, &data);
			ctx.add_certificate_authority(boost::asio::buffer(data, static_cast<std::size_t>(len)), ec);
		}
		BIO_free(bio);

		X509_free(x509);
	} while (!ec);

	CertCloseStore(hstore, 0);
}

inline void load_root_certificates(ssl::context& ctx)
{
	boost::system::error_code ec;
	load_root_certificates(ctx, ec);
	if (ec) throw boost::system::system_error{ ec };
}


int main() {

    // Create I/O context for asynchronous operations
    net::io_context ioc;

// Create SSL context with TLSv1.2
    ssl::context ctx{ssl::context::tlsv12_client};

// Load root certificates for server verification
    load_root_certificates(ctx);

// Resolve server endpoint
    tcp::resolver resolver(ioc);
    auto results = resolver.resolve("wss://api.gemini.com/v1/marketdata/BTCUSD", "443");

// Create a WebSocket stream over an SSL stream
    websocket::stream<beast::ssl_stream<tcp::socket>> ws(ioc, ctx);

// Connect to the server
    net::connect(ws.next_layer().next_layer(), results.begin(), results.end());

// Perform SSL handshake
    ws.next_layer().handshake(ssl::stream_base::client);

// Perform WebSocket handshake
    ws.handshake(host, "/");


    beast::flat_buffer buffer;  // Buffer to store incoming data

    ws.read(buffer);  // Read data from the WebSocket

    std::string message = beast::buffers_to_string(buffer.data());

    for(int i =0; i<100; i++ , ws.is_open())
    {
        ws.read(buffer);
        ws.read(buffer);  // Read data from the WebSocket

        std::string message = beast::buffers_to_string(buffer.data());

// Process the received message
        std::cout << "Received message: " << message << std::endl;
        // Process the received data
    }

    ws.close(websocket::close_code::normal);



    return 0;
}
