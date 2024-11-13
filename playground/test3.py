from telethon import TelegramClient
from telethon.tl.functions.messages import GetHistoryRequest

# Replace with your own values
api_id = '1941216'
api_hash = '9ff647ae847506caa21ada342f16644b'
phone_number = '+4915119472733'
chat_username = 'Fraikin Home Automation'  # Or use channel/group ID

# Initialize the client
client = TelegramClient('session_name', api_id, api_hash)


async def fetch_chat_history():
    # Connect to the client
    await client.start(phone_number)

    # Get the chat entity
    chat = await client.get_entity(chat_username)

    # Fetch the last 10 messages from the chat history
    history = await client(GetHistoryRequest(
        peer=chat,
        offset_id=0,
        offset_date=None,
        add_offset=0,
        limit=10,
        max_id=0,
        min_id=0,
        hash=0
    ))

    # Print each message
    messages = []
    for message in history.messages:
        messages.append(message.message)
    return messages


# Run the async function
with client:
    messages = client.loop.run_until_complete(fetch_chat_history())
    print(messages)
