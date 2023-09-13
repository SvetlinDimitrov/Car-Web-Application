import { useNavigate } from "react-router-dom";
import { useContext, useState, useEffect } from "react";

import { useForm } from "../../hooks/useForm";
import { AuthContext } from "../../contexts/UserAuth";
import { useErrorModelForm } from "../../hooks/useErrorForm";
import { createModel } from "../../utils/ModelService";

import { getAllBrands } from "../../utils/BrandService";

const keys = {
  name: "name",
  category: "category",
  imageUrl: "imageUrl",
  created: "created",
  generation: "generation",
  brandName: "brandName",
};
const initValues = {
  [keys.name]: "",
  [keys.category]: "",
  [keys.imageUrl]: "",
  [keys.created]: "",
  [keys.generation]: "",
  [keys.brandName]: "",
};

const ModelAdd = () => {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const { errors, onChangeError, onBluerError } = useErrorModelForm(initValues);
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
    const response = await createModel(user, values);
    if (!response.ok) {
      if (response.status === 401) {
        logout();
        navigate("/login");
      } else {
        const responseError = await response.text();

        Object.keys(keys).forEach((key) => {
          const object = {
            name: key,
            value: values[key],
          };
          onBluerError({ target: object });
          
        });
        setMainError(responseError);
      }
    } else {
      navigate("/models");
    }
  };

  const { values, onChange, onSubmit } = useForm(initValues, submitHandler);

  return (
    <div className="container">
      <h2 className="text-center text-white">Upload Model</h2>
      <form
        method="POST"
        onSubmit={onSubmit}
        className="main-form mx-auto col-md-8 d-flex flex-column justify-content-center"
      >
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label
              className="text-white font-weight-bold"
              htmlFor="name"
            >
              Name
            </label>
            <input
              id="name"
              type="text"
              className="form-control"
              rows="3"
              placeholder="Name"
              name={keys.name}
              value={values[keys.name]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            ></input>
            {errors[keys.name] !== "" && (
              <p className="errors alert alert-danger">{errors[keys.name]}</p>
            )}
          </div>
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="created" className="text-white font-weight-bold">
              Year Of Creation
            </label>
            <input
              id="created"
              type="number"
              min="0"
              step="1"
              className="form-control"
              placeholder="Suggested year"
              name={keys.created}
              value={values[keys.created]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            />
            {errors[keys.created] !== "" && (
              <p className="errors alert alert-danger">
                {errors[keys.created]}
              </p>
            )}
          </div>
        </div>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label
              className="text-center text-white font-weight-bold"
              htmlFor="category"
            >
              Category
            </label>
            <select
              id="category"
              className="form-control"
              name={keys.category}
              value={values[keys.category]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            >
              <option value="">- Select category type -</option>
              <option value="Car">Car</option>
              <option value="Buss">Buss</option>
              <option value="Truck">Truck</option>
              <option value="Motorcycle">Motorcycle</option>
            </select>
            {errors[keys.category] !== "" && (
              <p className="errors alert alert-danger">
                Engine type is required.
              </p>
            )}
          </div>
          <div className="form-group col-md-6 mb-3">
            <label
              className="text-center text-white font-weight-bold"
              htmlFor="brandName"
            >
              Brand
            </label>
            <select
              id="brandName"
              className="form-control"
              name={keys.brandName}
              value={values[keys.brandName]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            >
              <option value="">- Select a brand -</option>
              {brands.map((brand) => {
                return <option key={brand.id}>{brand.name}</option>;
              })}
              )
            </select>
            {errors[keys.brandName] !== "" && (
              <p className="errors alert alert-danger">Brand is required</p>
            )}
          </div>
        </div>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="generation" className="text-white font-weight-bold">
              Generation
            </label>
            <input
              id="generation"
              type="number"
              min="0"
              step="1"
              className="form-control"
              placeholder="Manufacturing year"
              name={keys.generation}
              value={values[keys.generation]}
              onChange={(e) => {
                onChange(e);
                onChangeError(e);
              }}
              onBlur={onBluerError}
            />
            {errors[keys.generation] !== "" && (
              <p className="errors alert alert-danger">
                {errors[keys.generation]}
              </p>
            )}
          </div>
        </div>

        <div className="form-group">
          <label className="text-white font-weight-bold" htmlFor="imageUrl">
            imageUrl
          </label>
          <input
            id="imageUrl"
            type="text"
            className="form-control"
            placeholder="imageUrl"
            name={keys.imageUrl}
            value={values[keys.imageUrl]}
            onChange={(e) => {
              onChange(e);
              onChangeError(e);
            }}
            onBlur={onBluerError}
          ></input>
          {errors[keys.imageUrl] !== "" && (
            <p className="errors alert alert-danger">{errors[keys.imageUrl]}</p>
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

export default ModelAdd;
