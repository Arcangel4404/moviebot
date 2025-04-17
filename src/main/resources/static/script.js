const chatMessages = document.getElementById('chat-messages');
const userInput = document.getElementById('user-input');
const sendBtn = document.getElementById('send-btn');

function appendMessage(text, sender) {
    const msg = document.createElement('div');
    msg.className = `message ${sender}`;
    msg.innerText = text;
    chatMessages.appendChild(msg);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

sendBtn.addEventListener('click', sendMessage);
userInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') sendMessage();
});

function sendMessage() {
    const message = userInput.value.trim();
    if (message === '') return;

    appendMessage(message, 'user');
    userInput.value = '';

    fetch(`/chat/message?text=${encodeURIComponent(message)}`)
        .then(res => res.text())
        .then(data => {
            appendMessage(data, 'bot');
        })
        .catch(() => {
            appendMessage("Sorry, something went wrong.", 'bot');
        });
}
