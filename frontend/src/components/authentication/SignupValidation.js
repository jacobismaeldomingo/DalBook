// SignupValidation.js

export const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@dal\.ca$/;
    return emailRegex.test(email);
  };
  
  export const validatePassword = (password) => {
    let validationErrors = [];
  
    if (password.length < 8) {
      validationErrors.push("Password must be at least 8 characters long.");
    }
    if (!/[a-z]/.test(password)) {
      validationErrors.push("Password must contain at least one lowercase letter.");
    }
    if (!/[A-Z]/.test(password)) {
      validationErrors.push("Password must contain at least one uppercase letter.");
    }
    if (!/\d/.test(password)) {
      validationErrors.push("Password must contain at least one number.");
    }
    if (!/[@$!%*?&]/.test(password)) {
      validationErrors.push("Password must contain at least one special character (@, $, !, %, *, ?, &).");
    }
  
    return validationErrors;
  };
  