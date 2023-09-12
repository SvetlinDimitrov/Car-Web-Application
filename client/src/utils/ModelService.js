export const getAllModel = async () => {
  const response = await fetch("http://127.0.0.1:8080/car/api/model");
  
  const result = await response.json();
  
    return result;
};
