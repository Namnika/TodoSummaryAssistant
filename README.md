# Todo Summary Assistant

I developed a full-stack application built with React (for the frontend) and Java, utilising the Spring Boot framework (for the backend), that manages users' tasks and generates summaries using the Cohere LLM (large language model) API. This API sends the summaries to a  Slack channel.

---
> _Meanwhile, install the app with a single click and receive summaries directly in your Slack channel!_
>
> <a href="https://slack.com/oauth/v2/authorize?client_id=8947962800497.8937211544354&scope=incoming-webhook,chat:write,commands&user_scope="><img alt="Add to Slack" height="40" width="139" src="https://platform.slack-edge.com/img/add_to_slack.png" srcSet="https://platform.slack-edge.com/img/add_to_slack.png 1x, https://platform.slack-edge.com/img/add_to_slack@2x.png 2x" /></a>

---

## Tech Stack

- **Frontend:** React.js
- **Backend:** Java (Spring Boot)
- **Database:** PostgreSQL (Supabase)
- **LLM Integration:** Cohere API
- **Slack Integration:** Incoming Webhooks

---

## Setup Instructions

1. Clone the repository: git clone https://github.com/Namnika/TodoSummaryAssistant.git
2. Install dependencies: `npm install`
3. Start the server: `npm start`
4. Open the application in your browser: http://localhost:3000 this will run your frontend React app
   - Make sure to run the backend server, run this command: `./mvnw spring-boot:run`

### Slack Setup Guidance

To integrate slack into this application, follow these steps:

1. Go to [Slack API: Incoming Webhooks](<https://api.slack.com/apps(https://api.slack.com/messaging/webhooks)>)
2. Create a new slack app from scratch
3. Enable incoming webhook and generate incoming webhook url.
   (dashboard page-> sidebar -> Incoming Webhooks -> turn it on -> generate url)
4. Copy that url and paste it to your `.env` file

### Cohere LLM API Setup Guidance

To integrate cohere api, follow below steps:

1. Sign up at [Cohere](https://cohere.com/).
2. Go to your dashboard and generate an API key.
3. Add this API key to your .env.

---

### API Endpoints

<table>
<thead>
  <tr>
	 <th>Method</th>
	 <th>Endpoint</th>
	 <th>Description</th>
  </tr>
  </thead>
  <tbody>
  <tr>
	 <td>GET</td>
	 <td>/todos</td>
	 <td>Fetch all todos</td>
  </tr>
  <tr>
	 <td>POST</td>
	 <td>/todos</td>
	 <td>Fetch all todos</td>
  </tr>
  <tr>
	 <td>DELETE</td>
	 <td>/todos/:id </td>
	 <td>Delete todo by ID </td>
  </tr>
  <tr>
	 <td>POST</td>
	 <td>/summarize</td>
	 <td>Generate summary and send to Slack</td>
  </tr>
  </tbody>
</table>
