import React, { useEffect, useState } from "react";
import axios from "axios";

const TodoPage = () => {
    const [input, setInput] = useState("");
    const [tasks, setTasks] = useState([]);
    const [summary, setSummary] = useState("");
    const [error, setError] = useState("");
    const [editingId, setEditingId] = useState(null);
    const [updateInput, setUpdateInput] = useState("");


    const apiBaseUrl = "http://localhost:8080/todos";


    // Getting todos
    const getTodo = async (e) => {
        try {
            const response = await axios.get(apiBaseUrl);


            if (!response.data || response.data.length === 0) {
                setError("No Tasks Added.");
                return;
            }
            console.log(response.data);

            setTasks(response.data);
        } catch (error) {
            setError("No Tasks Found! Please Add some tasks! " + error.message);
        }
    };

    useEffect(() => {
        getTodo().catch(console.error);
    }, []);


    // Adding todos
    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(apiBaseUrl, {
                id: tasks.length + 1,
                todoItem: input
            }, {
                headers: {
                    "Content-Type": "application/json",
                }
            });
            console.log(response.data);

            setTasks([...tasks, response.data]);
        } catch (error) {
            setError("Failed to Add Tasks !" + error.message);
        }
    };

    // Updating todos

    const handleEdit = (task) => {
        setEditingId(task.id);
        setUpdateInput(task.todoItem);
    };
    const handleUpdate = async () => {

        try {
            const response = await axios.put(`${apiBaseUrl}/update/${editingId}`, {
                id: editingId,
                todoItem: updateInput
            }, {
                headers: {
                    "Content-Type": "application/json",
                }
            });

            console.log(response.data);
            setTasks(
                tasks.map((t) =>
                    t.id === editingId ? { ...t, todoItem: response.data.todoItem } : t
                )
            );
            setEditingId(null)

        } catch (error) {
            setError("Failed to Update Todos: " + error.message);
        }
    }

    // deleting todos

    const handleDelete = async (id) => {
        try {
            await axios.delete(`${apiBaseUrl}/${id}`);
            setTasks(tasks.filter((task) => task.id !== id));
        } catch (error) {
            setError("Failed to Delete Todos: " + error.message);
        }
    };

    // summarize todos

    const handleSummarize = async () => {
        try {
            const response = await axios.post(`${apiBaseUrl}/summarize`);
            console.log(response.data);
            setSummary(response.data);
        } catch (error) {
            setError("Failed to Summarize Todos: " + error.message);
        }
    }


    return (
        <div className="TodoApp">
            <header className="TodoApp-header">
                <h2>Todo Summary Assistant</h2>
                <p>Summarize all your pending to-dos in a single click button!</p>
            </header>
            <section>
                <div className="todo-container">
                    <div className="todo-addtask">
                        <input
                            type="text"
                            name="todoinput"
                            value={input}
                            placeholder="Add Your Tasks"
                            onChange={(e) => setInput(e.target.value)}
                        />
                        <button type="submit" onClick={handleSubmit}>
                            Remind Me{" "}
                        </button>
                    </div>

                    <div className="todo-inner">
                        <div className="todo-lists">
                            {/* todos will show here from backend*/}
                            <ul>
                                {error ? (
                                    <div className="error-mesaage">
                                        <p>{error}</p>
                                    </div>
                                ) : (
                                    tasks.map((task, index) => {
                                        return (
                                            <div className="list">
                                                {editingId === task.id ? (
                                                    <input type="text" name="todoupdateinput" value={updateInput} onChange={(e) => setUpdateInput(e.target.value)} />

                                                ) :
                                                    <li key={index}>{task.todoItem}</li>

                                                }

                                                {editingId === task.id ? (
                                                    <button onClick={handleUpdate}>Update</button>
                                                ) : (
                                                    <button onClick={() => {
                                                        handleEdit(task)
                                                    }}>Edit</button>
                                                )}
                                                <button onClick={() => handleDelete(task.id)}>Delete</button>
                                            </div>
                                        );
                                    })
                                )}
                            </ul>
                        </div>
                    </div>
                    <div className="summary-container">
                        <div className="summary-button">
                            <button type="submit" onClick={handleSummarize}>Summarize Tasks</button>
                        </div>
                        <div className="summary-inner">
                            {/* Summary will generate here and show error messages*/}
                            <div className="summary-title">
                                <h3>Summary: </h3>
                            </div>
                            <div className="summary-text">
                                {summary ? (
                                    <p>{summary}</p>
                                ) : (
                                    <p>{error}</p>
                                )
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default TodoPage;
