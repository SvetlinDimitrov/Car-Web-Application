import { Link } from "react-router-dom";

const Error = () => {
  return (
    <div className="container-fluid">
      <h1 className="text-center text-white mt-5">404 NOT FOUND</h1>
      <p className="text-center text-white mt-5">
        <Link to={"/"}>HOME</Link>
      </p>
    </div>
  );
};

export default Error;
