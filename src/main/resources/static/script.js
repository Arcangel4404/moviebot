document.getElementById("user-input").addEventListener("keypress", function(e) {
  if (e.key === "Enter") sendMessage();
});

function sendMessage() {
  const input = document.getElementById("user-input");
  const message = input.value.trim();
  if (!message) return;

  addMessage("user", message);
  input.value = "";

  fetch(`http://localhost:8080/chat/message?text=${encodeURIComponent(message)}&user=Parinay`)
    .then(res => res.json())
    .then(data => handleBotResponse(data));
}

function addMessage(sender, text) {
  const msgDiv = document.createElement("div");
  msgDiv.className = `message ${sender}`;
  msgDiv.innerText = text;
  document.getElementById("chat-messages").appendChild(msgDiv);
  scrollToBottom();
}

function handleBotResponse(data) {
  if (data.type === "suggestion") {
    let list = "Suggestions:\n";
    data.movies.forEach(movie => {
      list += `${movie.title} (${movie.genre}) - ID ${movie.id}\n`;
    });
    addMessage("bot", list);
  } else if (data.type === "booking") {
    addMessage("bot", data.message);
  } else if (data.type === "booking_list") {
    let list = "Your Bookings:\n";
    data.bookings.forEach(b => {
      list += `${b.movieTitle} (ID ${b.bookingId}) for ${b.username}\n`;
    });
    addMessage("bot", list);
  } else {
    addMessage("bot", data.message || "I didn't understand that.");
  }
}

function scrollToBottom() {
  const chat = document.getElementById("chat-messages");
  chat.scrollTop = chat.scrollHeight;
}
