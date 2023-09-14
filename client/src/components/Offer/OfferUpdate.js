import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect, useContext } from "react";

import { getOfferById, updateOffer } from "../../utils/OfferService";
import { getAllBrands } from "../../utils/BrandService";

import { AuthContext } from "../../contexts/UserAuth";
import { useErrorOfferForm } from "../../hooks/useErrorForm";

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
const OfferUpdate = () => {
  const [mainError, setMainError] = useState("");
  const [brands, setBrands] = useState([]);
  const [offer, setOffer] = useState({});
  const { id } = useParams();
  const { user, logout } = useContext(AuthContext);
  const { errors, onChangeError, onBluerError } = useErrorOfferForm(initValues);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      const response = await getOfferById(id, user);
      if (!response.ok) {
        if (response.status === 401) {
          logout();
          navigate("/login");
        } else {
          navigate("/error");
        }
      } else {
        const data = await response.json();
        setOffer(data[0]);
      }
    };
    fetchData();
  }, [id, setOffer, user, logout, navigate]);

  useEffect(() => {
    const fetchData = async () => {
      const data = await getAllBrands();
      setBrands(data);
    };
    fetchData();
  }, []);

  const onChange = (e) => {
    const { name, value } = e.target;

    if (name === keys.modelName) {
      setOffer((state) => ({
        ...state,
        model: { ...state.model, name: value },
      }));
    } else {
      setOffer((state) => ({ ...state, [name]: value }));
    }
  };
  const onSubmit = async (e) => {
    e.preventDefault();

    const response = await updateOffer(user, offer);

    if (!response.ok) {
      if (response.status === 401) {
        logout();
        navigate("login");
      } else {
        const responseError = await response.json();

        Object.keys(keys).forEach((key) => {
          const object = {
            name: key,
            value: offer[key],
          };
          onBluerError({ target: object });
        });
        setMainError(responseError.messages.join('\n'));
      }
    } else {
      navigate(`/offers/details/${offer.id}`);
    }
  };

  return (
    <div className="container">
      <h2 className="text-center text-white">Update Offer</h2>
      {offer.id && (
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
                value={offer.model.name}
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
                value={offer[keys.price]}
                onChange={(e) => {
                  onChange(e);
                  onChangeError(e);
                }}
                onBlur={onBluerError}
              />
              {errors.price !== "" && (
                <p className="errors alert alert-danger">
                  {errors[keys.price]}
                </p>
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
                value={offer[keys.engine]}
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
                value={offer[keys.transmission]}
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
                value={offer[keys.year]}
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
                value={offer[keys.mileage]}
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
            <label
              className="text-white font-weight-bold"
              htmlFor="description"
            >
              Description
            </label>
            <textarea
              id="description"
              type="textarea"
              className="form-control"
              rows="3"
              placeholder="Description"
              name={keys.description}
              value={offer[keys.description]}
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
      )}
    </div>
  );
};

export default OfferUpdate;
