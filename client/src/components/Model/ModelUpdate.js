import { useNavigate, useParams } from "react-router-dom";
import { useContext, useState, useEffect, useRef } from "react";

import { AuthContext } from "../../contexts/UserAuth";
import { useErrorModelForm } from "../../hooks/useErrorForm";
import { getModelById, updateModel } from "../../utils/ModelService";

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

const ModelUpdate = () => {
  const { user, logout } = useContext(AuthContext);
  const { id } = useParams();
  const uploaded = useRef(false);
  const navigate = useNavigate();
  const { errors, onChangeError, onBluerError } = useErrorModelForm(initValues);
  const [mainError, setMainError] = useState("");
  const [brands, setBrands] = useState([]);
  const [model, setModel] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      const data = await getAllBrands();
      setBrands(data);
    };
    fetchData();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      const response = await getModelById(user, id);
      if (!response.ok) {
        if (response.status === 401) {
          logout();
          navigate("/login");
        } else {
          navigate("/error");
        }
      } else {
        const data = await response.json();
        const { id, name, category, imageUrl, created, generation } = data[0];
        setModel({
          id,
          name,
          category,
          imageUrl,
          created,
          generation,
          brandName: data[0].brand.name,
        });
      }
    };
    if (!uploaded.current) {
      uploaded.current = true;
      fetchData();
    }
  }, [id, logout, navigate, user]);

  const onChange = (e) => {
    const { name, value } = e.target;

    setModel((state) => ({ ...state, [name]: value }));
  };

  const onSubmit = async (e) => {
    e.preventDefault();

    const response = await updateModel(user, model);
    if (!response.ok) {
      if (response.status === 401) {
        logout();
        navigate("/login");
      } else {
        const responseError = await response.json();

        Object.keys(keys).forEach((key) => {
          const object = {
            name: key,
            value: model[key],
          };
          onBluerError({ target: object });
        });
        setMainError(responseError.messages.join('\n'));
      }
    } else {
      navigate("/models");
    }
  };
  return (
    <div className="container">
      {model.id && (
        <>
          <h2 className="text-center text-white">Change Model</h2>
          <form
            method="POST"
            onSubmit={onSubmit}
            className="main-form mx-auto col-md-8 d-flex flex-column justify-content-center"
          >
            <div className="row">
              <div className="form-group col-md-6 mb-3">
                <label className="text-white font-weight-bold" htmlFor="name">
                  Name
                </label>
                <input
                  id="name"
                  type="text"
                  className="form-control"
                  rows="3"
                  placeholder="Name"
                  name={keys.name}
                  value={model[keys.name]}
                  onChange={(e) => {
                    onChange(e);
                    onChangeError(e);
                  }}
                  onBlur={onBluerError}
                ></input>
                {errors[keys.name] !== "" && (
                  <p className="errors alert alert-danger">
                    {errors[keys.name]}
                  </p>
                )}
              </div>
              <div className="form-group col-md-6 mb-3">
                <label
                  htmlFor="created"
                  className="text-white font-weight-bold"
                >
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
                  value={model[keys.created]}
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
                  value={model[keys.category]}
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
                  value={model[keys.brandName]}
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
                <label
                  htmlFor="generation"
                  className="text-white font-weight-bold"
                >
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
                  value={model[keys.generation]}
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
                value={model[keys.imageUrl]}
                onChange={(e) => {
                  onChange(e);
                  onChangeError(e);
                }}
                onBlur={onBluerError}
              ></input>
              {errors[keys.imageUrl] !== "" && (
                <p className="errors alert alert-danger">
                  {errors[keys.imageUrl]}
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
        </>
      )}
    </div>
  );
};

export default ModelUpdate;
