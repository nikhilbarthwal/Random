import requests
import logging
import asyncio
import websockets


def get_session():
    token = '<<YOUR TOKEN>>'

    response = requests.post('https://api.tradier.com/v1/markets/events/session',
        data={},
        headers={'Authorization': 'Bearer ' + token, 'Accept': 'application/json'}
    )

    payload_response = response.json()
    if response.status_code != 200:
        logging.error("Could not get session:\n" + payload_response)
        exit(1)

    print(payload_response)
    json_response = payload_response['stream']
    session = json_response['sessionid']
    print(json_response['url'])
    print(session)
    return session


async def ws_connect():
    uri = "wss://ws.tradier.com/v1/markets/events"
    session = get_session()
    opt = 'SPY240920C00500000'
    payload = ('{"symbols": ["' + opt + '"], "sessionid": "' +
               session + '", "linebreak": true}')
    print(payload)
    async with websockets.connect(uri, ssl=True, compression=None) as websocket:
        await websocket.send(payload)
        print(f">>> {payload}")
        async for message in websocket:
            print(f"<<< {message}")

asyncio.run(ws_connect())

"""

{
    'stream': {
        'url': 'https://stream.tradier.com/v1/markets/events',
        'sessionid': 'dd0cb2db-81d3-4687-b54c-52742c1bb324'
    }
}


{
  "type": "quote",
  "symbol": "SPY240729C00480000",
  "bid": 66.85,
  "bidsz": 250,
  "bidexch": "C",
  "biddate": "1722015092000",
  "ask": 67.16,
  "asksz": 250,
  "askexch": "C",
  "askdate": "1722015086000"
}


{
  "type": "quote",
  "symbol": "SPY",
  "bid": 547.06,
  "bidsz": 2,
  "bidexch": "Q",
  "biddate": "1722015119000",
  "ask": 547.07,
  "asksz": 1,
  "askexch": "Q",
  "askdate": "1722015119000"
}

https://documentation.tradier.com/brokerage-api/trading/place-otoco-order


"""
