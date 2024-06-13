import { useState } from 'react';

function Login(){
    const [formData, setFormData] = useState({
        email:'', 
        password:'',
    });

    const handleChange = (event) => {
        setFormData({...formData, [event.target.name]: event.target.value, }); 
    };

    return (
        <div>
            <h1>Login Page</h1>
        <form>
        <label>
            Dal Email:
            <input type="email" name='email' required  onChange={handleChange} />
        </label><br />
        <label>
            Password:
            <input type="password" name='password' required onChange={handleChange} />
        </label><br />
        <button type="submit">Submit</button>
        </form>
        <p>
            <h3>Entered details:(for testing purpose)</h3>  {/* For testing only */}
            username: {formData.email}<br />
            password: {formData.password}<br />
        </p>
        </div>
    );
}
export default Login;