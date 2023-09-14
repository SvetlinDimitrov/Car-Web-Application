import { useNavigate } from "react-router-dom";
import { useContext, useState, useEffect } from "react";

import { useForm } from "../../hooks/useForm";
import { AuthContext } from "../../contexts/UserAuth";
import { useErrorOfferForm } from "../../hooks/useErrorForm";
import { createOffer } from "../../utils/OfferService";
import { getAllBrands } from "../../utils/BrandService";

const keys = {
  modelName: "modelName",
  price: "price",
  engine: "engine",
  year: "year",
  description: "description",
  mileage: "mileage",
  transmission: "transmission",
};
const initValues = {
  [keys.modelName]: "",
  [keys.price]: "",
  [keys.engine]: "",
  [keys.year]: "",
  [keys.description]: "",
  [keys.imageUrl]: "",
  [keys.mileage]: "",
  [keys.transmission]: "",
};
const OfferAdd = () => {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const { errors, onChangeError, onBluerError } = useErrorOfferForm(initValues);
  const [mainError, setMainError] = useState("");
  const [brands, setBrands] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const data = await getAllBrands();
      setBrands(data);
    };
    fetchData();
  }, []);

  const submitHandler = async (values) => {
    const response = await createOffer(user, values);

    if (!response.ok) {
      if (response.status === 401) {
        logout();
        navigate("/login");
      } else {
        const responseError = response.text();
        Object.keys(keys).forEach((key) => {
          const object = {
            name: key,
            value: values[key],
          };
          onBluerError({ target: object });
        });
        setMainError(responseError);
      }
    }else{
      navigate('/offers');
    }
  };

  const { values, onChange, onSubmit } = useForm(initValues, submitHandler);
  
  return (
    
    <div className="container">
      <h2 className="text-center text-white">Upload Offer</h2>
      <form
        method="POST"
        onSubmit={onSubmit}
        className="main-form mx-auto col-md-8 d-flex flex-column justify-content-center"
      >
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label
              className="text-center text-white font-weight-bold"
              htmlFor="model"
            >
              Model
            </label>
            <select
              id="model"
              className="form-control"
              name={keys.modelName}
              value={values[keys.modelName]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            >
              <option value="">- Select a model -</option>
              {brands.map((brand) => {
                return (
                  <optgroup key={brand.id} label={brand.name}>
                    {brand.modelList.map((model) => {
                      return <option key={model.id}>{model.name}</option>;
                    })}
                  </optgroup>
                );
              })}
            </select>
            {errors[keys.modelName] !== "" && (
              <p className="errors alert alert-danger">Model is required</p>
            )}
          </div>
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="price" className="text-white font-weight-bold">
              Price
            </label>
            <input
              id="price"
              type="number"
              min="0"
              step="100"
              className="form-control"
              placeholder="Suggested price"
              name={keys.price}
              value={values[keys.price]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            />
            {errors.price !== "" && (
              <p className="errors alert alert-danger">{errors[keys.price]}</p>
            )}
          </div>
        </div>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label
              className="text-center text-white font-weight-bold"
              htmlFor="engine"
            >
              Engine
            </label>
            <select
              id="engine"
              className="form-control"
              name={keys.engine}
              value={values[keys.engine]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            >
              <option value="">- Select engine type -</option>
              <option value="GASOLINE">GASOLINE</option>
              <option value="DIESEL">DIESEL</option>
              <option value="ELECTRIC">ELECTRIC</option>
              <option value="HYBRID">HYBRID</option>
            </select>
            {errors[keys.engine] !== "" && (
              <p className="errors alert alert-danger">
                Engine type is required.
              </p>
            )}
          </div>
          <div className="form-group col-md-6 mb-3">
            <label
              className="text-center text-white font-weight-bold"
              htmlFor="transmission"
            >
              Transmission
            </label>
            <select
              id="transmission"
              className="form-control"
              name={keys.transmission}
              value={values[keys.transmission]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            >
              <option value="">- Select transmission type -</option>
              <option value="MANUAL">MANUAL</option>
              <option value="AUTOMATIC">AUTOMATIC</option>
            </select>
            {errors[keys.transmission] !== "" && (
              <p className="errors alert alert-danger">
                Transmission type is required.
              </p>
            )}
          </div>
        </div>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="year" className="text-white font-weight-bold">
              Year
            </label>
            <input
              id="year"
              type="number"
              min="1900"
              max="2099"
              step="1"
              className="form-control"
              placeholder="Manufacturing year"
              name={keys.year}
              value={values[keys.year]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            />
            {errors[keys.year] !== "" && (
              <p className="errors alert alert-danger">{errors[keys.year]}</p>
            )}
          </div>
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="mileage" className="text-white font-weight-bold">
              Mileage
            </label>
            <input
              id="mileage"
              type="number"
              min="0"
              max="900000"
              step="1000"
              className="form-control"
              placeholder="Mileage in kilometers"
              name={keys.mileage}
              value={values[keys.mileage]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            />

            {errors[keys.mileage] !== "" && (
              <p className="errors alert alert-danger">
                {errors[keys.mileage]}
              </p>
            )}
          </div>
        </div>

        <div className="form-group">
          <label className="text-white font-weight-bold" htmlFor="description">
            Description
          </label>
          <textarea
            id="description"
            type="textarea"
            className="form-control"
            rows="3"
            placeholder="Description"
            name={keys.description}
            value={values[keys.description]}
            onChange={(e) => {
              onChange(e);
              onChangeError(e);
            }}
            onBlur={onBluerError}
          ></textarea>
          {errors[keys.description] !== "" && (
            <p className="errors alert alert-danger">
              {errors[keys.description]}
            </p>
          )}
        </div>
        {Object.values(errors).every((error) => error === "") &&
          mainError !== "" && (
            <p className="errors alert alert-danger">{mainError}</p>
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

export default OfferAdd;
