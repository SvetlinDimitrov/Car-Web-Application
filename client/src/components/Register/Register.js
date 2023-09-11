import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { useForm } from "../../hooks/useForm";
import { register } from "../../utils/UserService";
import { useErrorRegisterForm } from "../../hooks/useErrorForm";

const keys = {
  firstName: "firstName",
  lastName: "lastName",
  username: "username",
  password: "password",
};

const initValues = {
  [keys.username]: "",
  [keys.password]: "",
  [keys.firstName]: "",
  [keys.lastName]: "",
};
const Register = () => {
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const submitHandler = async (values) => {
    try {
      await register(values);
      navigate('/login')
    } catch (e) {
      setError(e.message);
    }
  };

  const { values, onChange, onSubmit } = useForm(initValues, submitHandler);
  const { errors, onChangeError } =useErrorRegisterForm(initValues);

  return (
    <div className="container">
      <h2 className="text-center text-white">Register User</h2>
      <form
        method="POST"
        className="main-form mx-auto col-md-8 d-flex flex-column justify-content-center"
        onSubmit={onSubmit}
      >
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="firstName" className="text-white font-weight-bold">
              First Name
            </label>
            <input
              id="firstName"
              type="text"
              min="2"
              max="20"
              className="form-control"
              placeholder="First name"
              name={keys.firstName}
              value={values[keys.firstName]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onChangeError}
              onClick={() => {
                setError("");
              }}
            />
            {errors[keys.firstName].length !== 0 && (
              <p className="errors alert alert-danger">
                {errors[keys.firstName]}
              </p>
            )}
          </div>
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="lastName" className="text-white font-weight-bold">
              Last Name
            </label>
            <input
              id="lastName"
              type="text"
              min="2"
              max="20"
              className="form-control"
              placeholder="Last name"
              name={keys.lastName}
              value={values[keys.lastName]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onChangeError}
              onClick={() => {
                setError("");
              }}
            />
            {errors[keys.lastName].length !== 0 && (
              <p className="errors alert alert-danger">
                {errors[keys.lastName]}
              </p>
            )}
          </div>
        </div>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="username2" className="text-white font-weight-bold">
              Username
            </label>
            <input
              autoComplete="username"
              id="username2"
              type="text"
              min="2"
              max="20"
              className="form-control"
              placeholder="username"
              name={keys.username}
              value={values[keys.username]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onChangeError}
              onClick={() => {
                setError("");
              }}
            />
            {errors[keys.username].length !== 0 && (
              <p className="errors alert alert-danger">
                {errors[keys.username]}
              </p>
            )}
          </div>
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="password2" className="text-white font-weight-bold">
              Password
            </label>
            <input
              autoComplete="current-password"
              id="password2"
              type="password"
              min="2"
              max="20"
              className="form-control"
              placeholder="Password"
              name={keys.password}
              value={values[keys.password]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onChangeError}
              onClick={() => {
                setError("");
              }}
            />
            {errors[keys.password].length !== 0 && (
              <p className="errors alert alert-danger">
                {errors[keys.password]}
              </p>
            )}
          </div>
        </div>

        {error.length !== 0 && (
          <p className="errors alert alert-danger">{error}</p>
        )}
        <div className="row">
          <div className="col col-md-4">
            <div className="button-holder d-flex">
              <input
                type="submit"
                className="btn btn-info btn-lg"
                value="Submit Offer"
              />
            </div>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Register;
