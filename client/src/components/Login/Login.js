import { useState , useContext} from "react";
import { useNavigate } from "react-router-dom";

import { login } from "../../utils/UserService";
import { useForm } from "../../hooks/useForm";
import { useErrorLoginForm } from '../../hooks/useErrorForm';
import { AuthContext } from "../../contexts/UserAuth";

const keys = {
  username : 'username',
  password : 'password'
}

const initValues = {
  [keys.username] : '',
  [keys.password] : '',
}

const Login = () => {
  const [error , setError] = useState('');
  const { loginUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const submitHandler = async (values) => {
  
      const response = await login(values);
      const responseData = await response.json();

      if(!response.ok){
        Object.keys(keys).forEach((key) => {
          const object = {
            name: key,
            value: values[key],
          };
          onBluerError({ target: object });
        });
        setError(responseData.messages.join('\n'));
      }else{
        loginUser(responseData);
        navigate('/');
      }   

  }

  const {values , onChange , onSubmit , } = useForm(initValues,submitHandler);
  const {errors, onChangeError ,onBluerError} = useErrorLoginForm(initValues);

  return (
    <div className="container">
      <h2 className="text-center text-white">Login</h2>
      <form
        method="POST"
        onSubmit={onSubmit}
        className="main-form mx-auto col-md-8 d-flex flex-column justify-content-center"
      >
        <div className="form-group">
          <label htmlFor="username" className="text-white font-weight-bold">
            Username
          </label>
          <input
           autoComplete={keys.username}
            id="username"
            name={keys.username}
            type="text"
            className="form-control"
            placeholder={keys.username}
            value={values[keys.username]}
            onChange={(e) => {
              onChange(e);
              onChangeError(e);
            }}
            onBlur={onBluerError}
            onClick={()=>{setError('')}}
          />
          {errors[keys.username].length !== 0 &&
          (<p className="errors alert alert-danger">{errors[keys.username]}</p>)}
        </div>
        <div className="form-group">
          <label htmlFor="password" className="text-white font-weight-bold">
            Password
          </label>
          <input
            autoComplete="current-password"
            id="password"
            name={keys.password}
            type="password"
            min="2"
            max="20"
            className="form-control"
            placeholder={keys.password}
            value={values[keys.password]}
            onChange={(e) => {
              onChange(e);
              onChangeError(e);
            }}
            onBlur={onBluerError}
            onClick={()=>{setError('')}}
          />
          {errors[keys.password].length !== 0 &&
          (<p className="errors alert alert-danger">{errors[keys.password]}</p>)}
        </div>
        {Object.values(errors).every((error) => error === "") &&
          error !== "" && 
        (<p className="errors alert alert-danger">{error}</p>)}
      
        <div className="row">
          <div className="col col-md-4">
            <div className="button-holder d-flex">
              <input
                type="submit"
                className="btn btn-info btn-lg"
                
              />
            </div>
          </div>
        </div>
      </form>
    </div>
  );
};
export default Login;