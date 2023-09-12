import { useState, useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { AuthContext } from "../../contexts/UserAuth";
import { useErrorBrandForm } from "../../hooks/useErrorForm";
import { getBrandById  , updateBrand} from "../../utils/BrandService";

const keys = {
  name: "name",
  created: "created",
};

const initValues = {
  [keys.name]: "",
  [keys.created]: "",
};
const BrandChange = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const { user } = useContext(AuthContext);
  const [ brand, setBrand ] = useState({});

  const [mainError, setMainError] = useState("");
  const { errors, onBluerError, onChangeError } = useErrorBrandForm(initValues);

  useEffect(() => {
    const fetchData = async () => {
      const data = await getBrandById(user, id);
      setBrand(data[0]);
    };
    fetchData();
  }, [id , user]);

  const onChange = (e) => {
    const { name, value } = e.target;

    setBrand((state) => ({ ...state, [name]: value }));
  };
  const onSubmit = async (e) => {
    e.preventDefault();

    try {
      await updateBrand(user, brand);
      navigate("/brands");
    } catch (e) {
      Object.keys(keys).forEach((key) => {
        const object = {
          name: key,
          value: brand[key],
        };
        onBluerError({ target: object });
      });
      setMainError(e.message);
    }
  };

  return (
    <div className="container">
      <h2 className="text-center text-white">Brand</h2>
      {brand.id && (
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
              min="2"
              max="50"
              className="form-control"
              placeholder={keys.name}
              value={brand[keys.name]}
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
              value={brand[keys.created]}
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
              <p className="errors alert alert-danger">
                {errors[keys.created]}
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
                <input type="submit" className="btn btn-info btn-lg" />
              </div>
            </div>
          </div>
        </form>
      )}
    </div>
  );
};

export default BrandChange;
