import { Link } from "react-router-dom";
import { FaUser, FaLock, FaEnvelope } from "react-icons/fa";
import { useState } from "react";
import { AddressForm } from "./Address";
import { useNavigate } from "react-router-dom";

const RegisterPage = () => {
  const [accountname, setName] = useState("");
  const [passwords, setpassword] = useState("");
  const [email, setEmail] = useState("");
  const [firstname, setFirstName] = useState("");
  const [lastname, setLastName] = useState("");
  const [agree, setAgree] = useState(false);
  const [address, setAddress] = useState("");
  const [coordinates, setCoordinates] = useState({ lat: null, lng: null });
  const navigate = useNavigate();

  const handleClick = (e) => {
    e.preventDefault();
    const { lat, lng } = coordinates;
    if (!agree) {
      alert("Please agree to the terms and conditions to register.");
      return;
    }
    const user = {
      accountname,
      passwords,
      email,
      address,
      firstname,
      lastname,
      latitude: lat,
      longitude: lng,
    };
    console.log(user);
    fetch("http://localhost:8081/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    })
      .then(() => {
        alert("Account register succefully");
        navigate("/");
      })
      .catch((error) => {
        alert("Accountname been taken, please try another one");
      });
  };
  return (
    <div className="form-box register">
      <form action="">
        <h2>Registration</h2>
        <div className="input-box">
          <input
            type="text"
            placeholder="Account Name"
            required
            value={accountname}
            onChange={(e) => setName(e.target.value)}
          />
          <FaUser className="icon" />
        </div>
        <div>
          <input
            type="password"
            placeholder="Password"
            required
            value={passwords}
            onChange={(e) => setpassword(e.target.value)}
          />
          <FaLock className="icon" />
        </div>
        <div className="input-box">
          <input
            type="email"
            placeholder="Email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <FaEnvelope className="icon" />
        </div>
        <div className="input-box">
          <input
            type="text"
            placeholder="First Name"
            required
            value={firstname}
            onChange={(e) => setFirstName(e.target.value)}
          />
        </div>
        <div className="input-box">
          <input
            type="text"
            placeholder="Last Name"
            required
            value={lastname}
            onChange={(e) => setLastName(e.target.value)}
          />
        </div>
        <AddressForm
          address={address}
          setAddress={setAddress}
          setCoordinates={setCoordinates}
        />
        <div className="remember-forgot">
          <label>
            <input
              type="checkbox"
              checked={agree}
              onChange={(e) => setAgree(e.target.checked)}
              required
            />
            I agree to the term & conditions
          </label>
          <button
            type="submit"
            className="btn"
            onClick={handleClick}
            disabled={!agree}
          >
            Register
          </button>
          <div className="register-link">
            <p>
              Already have an account?<Link to="/">Login</Link>
            </p>
          </div>
        </div>
      </form>
    </div>
  );
};
export { RegisterPage };




// import { Link } from "react-router-dom";
// import { FaUser, FaLock, FaEnvelope } from "react-icons/fa";
// import { useState } from "react";
// import { AddressForm } from "./Address";
// import { useNavigate } from "react-router-dom";


// const RegisterPage = () => {
//   const [accountname, setName] = useState("");
//   const [passwords, setpassword] = useState("");
//   const [email, setEmail] = useState("");
//   const [firstname, setFirstName] = useState("");
//   const [lastname, setLastName] = useState("");
//   const [agree, setAgree] = useState(false);
//   const [address, setAddress] = useState("");
//   const [coordinates, setCoordinates] = useState({ lat: null, lng: null });
//   const navigate = useNavigate();

//   const handleClick = (e) => {
//     e.preventDefault();
//     const { lat, lng } = coordinates;
//     if (!agree) {
//       alert("Please agree to the terms and conditions to register.");
//       return;
//     }
//     const user = {
//       accountname,
//       passwords,
//       email,
//       address,
//       firstname,
//       lastname,
//       latitude: lat,
//       longitude: lng,
//     };
//     console.log(user);
//     fetch("http://localhost:8081/api/auth/register", {
//       method: "POST",
//       headers: { "Content-Type": "application/json" },
//       body: JSON.stringify(user),
//     })
//       .then(() => {
//         alert("Account register succefully");
//         navigate("/");
//       })
//       .catch((error) => {
//         alert("Accountname been taken, please try another one");
//       });
//   };
//   return (
//     <div className="form-box register">
//       <form action="">
//         <h2>Registration</h2>
//         <div className="input-box">
//           <input
//             type="text"
//             placeholder="Account Name"
//             required
//             value={accountname}
//             onChange={(e) => setName(e.target.value)}
//           />
//           <FaUser className="icon" />
//         </div>
//         <div>
//           <input
//             type="password"
//             placeholder="Password"
//             required
//             value={passwords}
//             onChange={(e) => setpassword(e.target.value)}
//           />
//           <FaLock className="icon" />
//         </div>
//         <div className="input-box">
//           <input
//             type="email"
//             placeholder="Email"
//             required
//             value={email}
//             onChange={(e) => setEmail(e.target.value)}
//           />
//           <FaEnvelope className="icon" />
//         </div>
//         <div className="input-box">
//           <input
//             type="text"
//             placeholder="First Name"
//             required
//             value={firstname}
//             onChange={(e) => setFirstName(e.target.value)}
//           />
//         </div>
//         <div className="input-box">
//           <input
//             type="text"
//             placeholder="Last Name"
//             required
//             value={lastname}
//             onChange={(e) => setLastName(e.target.value)}
//           />
//         </div>
//         <AddressForm
//           address={address}
//           setAddress={setAddress}
//           setCoordinates={setCoordinates}
//         />
//         <div className="remember-forgot">
//           <label>
//             <input
//               type="checkbox"
//               checked={agree}
//               onChange={(e) => setAgree(e.target.checked)}
//               required
//             />
//             I agree to the term & conditions
//           </label>
//           <button
//             type="submit"
//             className="btn"
//             onClick={handleClick}
//             disabled={!agree}
//           >
//             Register
//           </button>
//           <div className="register-link">
//             <p>
//               Already have an account?<Link to="/">Login</Link>
//             </p>
//           </div>
//         </div>
//       </form>
//     </div>
//   );
// };
// export { RegisterPage };
