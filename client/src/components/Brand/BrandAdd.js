import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";

import { AuthContext } from "../../contexts/UserAuth";

import { useForm } from "../../hooks/useForm";
import { useErrorBrandForm } from "../../hooks/useErrorForm";
import { createBrand } from "../../utils/BrandService";

const keys = {
  name: "name",
  created: "created",
};

const initValues = {
  [keys.name]: "",
  [keys.created]: "",
};

const BrandAdd = () => {
  const navigate = useNavigate();
  const { user , logout } = useContext(AuthContext);
  const [mainError, setMainError] = useState("");
  const { errors, onBluerError, onChangeError } = useErrorBrandForm(initValues);

  const handleSubmit = async (data) => {
    const response = await createBrand(user, data);
    if(!response.ok){
      if(response.status === 401){
        logout();
        navigate("/");
      }else{
        const errorMessage = await response.json();
        Object.keys(keys).forEach((key) => {
          const object = {
            name: key,
            value: data[key],
          };
          onBluerError({ target: object });
        });
        setMainError(errorMessage.messages.join('\n'));
      }
    }else{
      navigate("/brands");
    }
  };

  const { values, onChange, onSubmit } = useForm(initValues, handleSubmit);
  return (
    <div className="container">
      <h2 className="text-center text-white">Create Brand</h2>
      <form
        method="POST"
        onSubmit={onSubmit}
        className="main-form mx-auto col-md-8 d-flex flex-column justify-content-center"
      >
        <div className="form-group">
          <label htmlFor="username" className="text-white font-weight-bold">
            Name
          </label>
          <input
            autoComplete={keys.name}
            id="username"
            name={keys.name}
            type="text"
            className="form-control"
            placeholder={keys.name}
            value={values[keys.name]}
            onChange={(e) => {
              onChange(e);
              onChangeError(e);
            }}
            onBlur={onBluerError}
            onClick={() => {
              setMainError("");
            }}
          />
          {errors[keys.name].length !== 0 && (
            <p className="errors alert alert-danger">{errors[keys.name]}</p>
          )}
        </div>
        <div className="form-group">
          <label htmlFor="date" className="text-white font-weight-bold">
            Name
          </label>
          <input
            id="date"
            type="date"
            value={values[keys.created]}
            name={keys.created}
            onChange={(e) => {
              onChange(e);
              onChangeError(e);
            }}
            onBlur={onBluerError}
            onClick={() => {
              setMainError("");
            }}
          />
          {errors[keys.created].length !== 0 && (
            <p className="errors alert alert-danger">{errors[keys.created]}</p>
          )}
        </div>
        {Object.values(errors).every((error) => error === "") &&
          mainError !== "" && (
            <p className="errors alert alert-danger">{mainError}</p>
          )}

        <div className="row">
          <div className="col col-md-4">
            <div className="button-holder d-flex">
              <input type="submit" className="btn btn-info btn-lg" />
            </div>
          </div>
        </div>
      </form>
    </div>
  );
};

export default BrandAdd;
