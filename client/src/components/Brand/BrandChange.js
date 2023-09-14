import { useState, useContext, useEffect , useRef } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { AuthContext } from "../../contexts/UserAuth";
import { useErrorBrandForm } from "../../hooks/useErrorForm";
import { getBrandById, updateBrand } from "../../utils/BrandService";

const keys = {
  name: "name",
  created: "created",
};

const initValues = {
  [keys.name]: "",
  [keys.created]: "",
};
const BrandChange = () => {
  const hasRunRef = useRef(false);
  const navigate = useNavigate();
  const { id } = useParams();
  const { user, logout } = useContext(AuthContext);
  const [brand, setBrand] = useState({});

  const [mainError, setMainError] = useState("");
  const { errors, onBluerError, onChangeError } = useErrorBrandForm(initValues);

  useEffect(() => {
    const fetchData = async () => {
      const response = await getBrandById(user, id);
      if(!response.ok){
        if(response.status === 401){
          logout();
          navigate('/login');
        }else {
          const responseError = await response.text();
          Object.keys(keys).forEach((key) => {
            const object = {
              name: key,
              value: brand[key],
            };
            onBluerError({ target: object });
          });
          setMainError(responseError.message);
        }
      }else{
        const data = await response.json();
        setBrand(data[0]);
      }  
    };
    if(!hasRunRef.current){
      hasRunRef.current = true;
      fetchData();
    }
    
  }, [id, user , brand , logout , navigate , onBluerError]);

  const onChange = (e) => {
    const { name, value } = e.target;

    setBrand((state) => ({ ...state, [name]: value }));
  };
  const onSubmit = async (e) => {
    e.preventDefault();

    const response = await updateBrand(user, brand);
    if (!response.ok) {
      if (response.status === 401) {
        logout();
        navigate("/login");
      } else {
        const responseError = await response.json();
        Object.keys(keys).forEach((key) => {
          const object = {
            name: key,
            value: brand[key],
          };
          onBluerError({ target: object });
        });
        setMainError(responseError.messages.join('\n'));
      }
    } else {
      navigate("/brands");
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
