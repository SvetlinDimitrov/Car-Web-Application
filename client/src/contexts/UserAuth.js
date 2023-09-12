import { createContext, useState } from "react";

export const AuthContext = createContext();

const UserAuthProvider = ({ children }) => {
  const [user, setUser] = useState(()=>{
    const user = localStorage.getItem('user');

    if(user !== null && user !== undefined){
      return JSON.parse(user);
    }

    return {};
  });

  const loginUser = (userToLogin) =>{
    setUser(userToLogin);
    localStorage.setItem('user' , JSON.stringify(userToLogin));
  }
  const logout = () => {
    setUser({});
    localStorage.removeItem('user');
  }

  const authUser = { user , loginUser ,logout };
  return (
    <AuthContext.Provider value={authUser}>{children}</AuthContext.Provider>
  );
};
export default UserAuthProvider;
