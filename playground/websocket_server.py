# websocket_server.py
import asyncio
import threading
import websockets


async def echo(websocket, path):
    async for message in websocket:
        print(f"Received message from client: {message}")
        await websocket.send(f"Echo: {message}")


async def main():
    async with websockets.serve(echo, "192.168.1.111", 60141):
        print("WebSocket server is running on ws://192.168.1.111:60141")
        await asyncio.Future()  # Run until canceled


# try:
#     asyncio.run(main())
# except KeyboardInterrupt:
#     print("Server stopped.")


# Define the WebSocket handler in an async function
async def websocket_handler(uri):
    async with websockets.connect(uri) as websocket:
        await websocket.send("Hello WebSocket!")
        while True:
            message = await websocket.recv()
            print("Received:", message)


# Run the WebSocket in a new thread
def start_websocket():
    asyncio.run(main())


# Start the WebSocket connection in a thread
# uri = "ws://192.168.1.111:60141"  # Replace with your WebSocket URL
websocket_thread = threading.Thread(target=start_websocket)
websocket_thread.start()

# Now you can continue running other non-blocking code here
print("WebSocket is running in a separate thread.")
while True:
    print("What is up")
    import time

    time.sleep(1)
