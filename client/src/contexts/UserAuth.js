import { createContext, useState } from "react";

export const AuthContext = createContext();

const UserAuthProvider = ({ children }) => {
  const [user, setUser] = useState({});

  const loginUser = (userToLogin) =>{
    setUser(userToLogin);
  }

  const authUser = { user , loginUser };
  return (
    <AuthContext.Provider value={authUser}>{children}</AuthContext.Provider>
  );
};
export default UserAuthProvider;
