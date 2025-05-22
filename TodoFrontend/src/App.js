import './App.css';
import TodoPage from './pages/TodoPage';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<TodoPage />} />
      </Routes>
    </Router>
  );
}

export default App;
