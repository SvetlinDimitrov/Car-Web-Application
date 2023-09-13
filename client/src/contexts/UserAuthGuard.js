import { useContext } from "react";
import { Navigate , Outlet } from "react-router-dom";
import { AuthContext } from "./UserAuth";

const UserAuthGuard = () => {
  const { user } = useContext(AuthContext);

  if (user.id) {
    return <Outlet/>;
  } else {
    return <Navigate to="/login" />;
  }

};

export default UserAuthGuard;
