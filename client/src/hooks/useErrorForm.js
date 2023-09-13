import { useState } from "react";

export const useErrorLoginForm = (initValues) => {
  const [errors, setErrors] = useState(initValues);

  const onBluerError = (e) => {
    const { name, value } = e.target;

    checkEmpty(name, value, "Should not be empty", setErrors);
  };

  const onChangeError = (e) => {
    const { name } = e.target;

    setErrors((state) => ({ ...state, [name]: "" }));
  };

  return { errors, onChangeError, onBluerError };
};

export const useErrorRegisterForm = (initValues) => {
  const [errors, setErrors] = useState(initValues);

  const onBlurError = (e) => {
    const { name, value } = e.target;
    checkEmpty(name, value, "Should not be empty", setErrors);
    if (name === "username") {
      checkLength(name, value, 4, "Should be at least 4 characters", setErrors);
    }
    if (name === "password") {
      checkLength(name, value, 5, "Should be at least 5 characters", setErrors);
    }
    if (name === "firstName" || name === "lastName") {
      checkLength(name, value, 3, "Should be at least 3 characters", setErrors);
    }
  };
  const onChangeError = (e) => {
    const { name } = e.target;

    setErrors((state) => ({ ...state, [name]: "" }));
  };

  return { errors, onChangeError, onBlurError };
};

export const useErrorOfferForm = (initValues) => {
  const [errors, setErrors] = useState(initValues);

  const onBluerError = (e) => {
    const { name, value } = e.target;

    if (errors[name] === "") {
      checkEmpty(name, value, "Cannot be empty", setErrors);
    }
    if (name === "description" && errors[name] === "") {
      checkLength(
        name,
        value,
        10,
        "Description should be at least 10 characters",
        setErrors
      );
    }
    if (
      (name === "mileage" || name === "price" || name === "year") &&
      errors[name] === ""
    ) {
      checkNegative(name, value, "Should be positive", setErrors);
    }
    if (name === "year") {
      checkYearIfIsInTheFuture(
        name,
        value,
        "Year must not be in the future",
        setErrors
      );
    }
  };
  const onChangeError = (e) => {
    const { name } = e.target;

    setErrors((state) => ({ ...state, [name]: "" }));
  };

  return { errors, onBluerError, onChangeError };
};

export const useErrorModelForm = (initValues) => {
  const [errors, setErrors] = useState(initValues);

  const onBluerError = (e) => {
    const { name, value } = e.target;

    if (errors[name] === "") {
      if(name !== 'imageUrl'){
        checkEmpty(name, value, "Cannot be empty", setErrors);
      }
    }
    
  };

  const onChangeError = (e) => {
    const { name } = e.target;

    setErrors((state) => ({ ...state, [name]: "" }));
  };

  return { errors, onBluerError, onChangeError };
};

export const useErrorBrandForm = (initValues) => {
  const [ errors, setErrors ] = useState(initValues);

  const onBluerError = (e) => {
    const { name, value } = e.target;

    checkEmpty(name , value , 'Should not be empty' , setErrors);
    if(name === 'name'){
      checkLength(name , value ,4, 'Must be at least 4 chars long' , setErrors)
    }
    if(name === 'created'){
      checkDataIsInTheFuture(name , value , 'Data cannot be in the future', setErrors);
    }
  };
  
  const onChangeError = (e) => {
    const { name } = e.target;

    setErrors((state) => ({ ...state, [name]: "" }));
  };
  return { errors, onBluerError, onChangeError };
};

const checkEmpty = (name, value, errorMessage, setErrors) => {
  if (value.length === 0) {
    setErrors((state) => ({ ...state, [name]: errorMessage }));
  }
};

const checkLength = (name, value, number, errorMessage, setErrors) => {
  if (value.length < parseInt(number)) {
    setErrors((state) => ({
      ...state,
      [name]: errorMessage,
    }));
  }
};

const checkNegative = (name, value, errorMessage, setErrors) => {
  if (value < 0) {
    setErrors((state) => ({
      ...state,
      [name]: errorMessage,
    }));
  }
};
const checkYearIfIsInTheFuture = (name, value, errorMessage, setErrors) => {
  const currentYear = new Date().getFullYear();

  if (currentYear < value) {
    setErrors((state) => ({ ...state, [name]: errorMessage }));
  }
};

const checkDataIsInTheFuture = (name , value , errorMessage , setErrors ) =>{
  const givenDate = new Date(value);

  
  const currentDate = new Date();
  
  
  if (givenDate.getTime() > currentDate.getTime()) {
    setErrors((state) => ({ ...state, [name]: errorMessage }));
  } 
};
