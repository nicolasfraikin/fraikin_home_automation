import asyncio
import websockets


async def send_message():
    uri = "ws://192.168.1.111:60141"  # Replace with your WebSocket server's URI
    async with websockets.connect(uri) as websocket:
        message = "Hello from Python1!"
        await websocket.send(message)
        print(f"Sent message: {message}")

        response = await websocket.recv()
        print(f"Received response: {response}")


# Run the async function
def send_it():
    asyncio.run(send_message())


send_it()
