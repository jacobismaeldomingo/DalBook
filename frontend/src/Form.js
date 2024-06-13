import { useState } from 'react';

export function Form(){

        const [formData, setFormData] = useState({
            firstname:'', 
            lastname: '',
            email: '',
            password:'',
        });

        const handleChange = (event) => {
            setFormData({...formData, [event.target.name]: event.target.value, }); 
        };

    return (
        <div>
            <h1>Registration Page</h1>
        <form>
        <label>
            Enter your First Name:
            <input type="text" name='firstname'  onChange={handleChange} />
        </label><br />
        <label>
            Enter your Last Name:
            <input type="text" name='lastname' onChange={handleChange} />
        </label><br />
        <label>
            Enter your Dal Email:
            <input type="email" name='email' onChange={handleChange} />
        </label><br />
        <label>
            Set your password:s
            <input type="password" name='password' onChange={handleChange} />
        </label><br />
        <button type="submit">Submit</button>
        </form>
        <p>
            <h3>Entered details:(for testing purpose)</h3>  {/* For testing only */}
            fname: {formData.firstname}<br />
            lname: {formData.lastname}<br />
            email: {formData.email}<br />
            password: {formData.password}<br />
      </p>
        </div>
    );
}

