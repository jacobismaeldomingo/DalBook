import { useState } from 'react';

function Form(){

        const [formData, setFormData] = useState({
            firstname:'', 
            lastname: '',
            email: '',
            password:'',
        });

        const handleChange = (event) => {
            setFormData({[event.target.name]: event.target.value, }); 
            console.log(formData.name); // Log the updated state
        };

    return (
        <div>
            <h1>Registration Page</h1>
        <form>
        <label>
            Enter your First Name:
            <input type="text"  onChange={handleChange} />
        </label><br />
        <label>
            Enter your Last Name:
            <input type="text"  onChange={handleChange} />
        </label><br />
        <label>
            Enter your Dal Email:
            <input type="email"  onChange={handleChange} />
        </label><br />
        <label>
            Set your password:
            <input type="password"  onChange={handleChange} />
        </label><br />
        <button type="submit">Submit</button>
        </form>
        <p> fname:{formData.firstname}<br />
            lname:{}<br />
            email:{}<br />
            password:{}
            </p>
        </div>
    );
}
export default Form;