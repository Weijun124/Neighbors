import { Link } from "react-router-dom";
import { FaUser, FaLock } from "react-icons/fa";
import { useRef, useState, useEffect, useContext } from "react";
import { AuthProvider, useAuth } from "../AuthContext.jsx";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const navigate = useNavigate();
  const userRef = useRef();
  const errRef = useRef();
  const { login } = useAuth();

  const [accountname, setAccountName] = useState("");
  const [password, setpassword] = useState("");
  const [errMsg, setErrMsg] = useState("");

  useEffect(() => {
    console.log(userRef.current); // Check what it outputs
    if (userRef.current) {
      userRef.current && userRef.current.focus();
    }
  }, []);

  useEffect(() => {
    setErrMsg("");
  }, [accountname, password]);

  const handleClick = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(
        "http://localhost:8081/api/auth/authenticate",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            accountname: accountname,
            passwords: password,
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.text(); // Could be text if not JSON
        throw new Error(errorData || "Unable to login");
      }

      const data = await response.json();
      // localStorage.setItem('jwtToken', data.token);
      navigate("/home", { state: { user: data } });
      login(data.token);
      // navigate("/home", { replace: true });
      // Redirect on success
    } catch (err) {
      console.error(err);
      setErrMsg("Login Failed. Check username and password.");
      errRef.current && errRef.current.focus();
    }
  };
  return (
    <div className="wrapper">
      <div className="form-box login">
        <form action="">
          <h2>Login</h2>
          <div className="input-box">
            <input
              type="text"
              placeholder="Username"
              ref={userRef}
              required
              value={accountname}
              onChange={(e) => setAccountName(e.target.value)}
            />
            <FaUser className="icon" />
          </div>
          <div>
            <input
              type="password"
              placeholder="Password"
              required
              value={password}
              onChange={(e) => setpassword(e.target.value)}
            />
            <FaLock className="icon" />
          </div>

          <div className="remember-forgot">
            <label>
              <input type="checkbox" />
              Remember me
            </label>
            <a href="#">Forgot Password?</a>
            <button type="submit" className="btn" onClick={handleClick}>
              Login
            </button>
            <div className="resiter-link">
              <p>
                Don't have an account? <Link to="/register">Register</Link>
              </p>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export { LoginPage };




// import { Link } from "react-router-dom";
// import { FaUser, FaLock } from "react-icons/fa";
// import { useRef, useState, useEffect, useContext } from "react";
// import { AuthProvider, useAuth } from "../AuthContext.jsx";
// import { useNavigate } from "react-router-dom";
// import "./comp.css";

// const LoginPage = () => {
//   const navigate = useNavigate();
//   const userRef = useRef();
//   const errRef = useRef();
//   const { login } = useAuth();

//   const [accountname, setAccountName] = useState("");
//   const [password, setpassword] = useState("");
//   const [errMsg, setErrMsg] = useState("");

//   useEffect(() => {
//     if (userRef.current) {
//       userRef.current && userRef.current.focus();
//     }
//   }, []);

//   useEffect(() => {
//     setErrMsg("");
//   }, [accountname, password]);

//   const handleClick = async (e) => {
//     e.preventDefault();
//     try {
//       const response = await fetch(
//         "http://localhost:8081/api/auth/authenticate",
//         {
//           method: "POST",
//           headers: {
//             "Content-Type": "application/json",
//           },
//           body: JSON.stringify({
//             accountname: accountname,
//             passwords: password,
//           }),
//         }
//       );

//       if (!response.ok) {
//         const errorData = await response.text(); // Could be text if not JSON
//         throw new Error(errorData || "Unable to login");
//       }

//       const data = await response.json();
//       navigate("/home", { state: { user: data } });
//     } catch (err) {
//       console.error(err);
//       setErrMsg("Login Failed. Check username and password.");
//       errRef.current && errRef.current.focus();
//     }
//   };
//   return (
//     <div className="wrapper">
//       <div className="form-box login">
//         <form action="">
//           <h2>Login</h2>
//           <div className="input-box">
//             <input
//               type="text"
//               placeholder="Username"
//               ref={userRef}
//               required
//               value={accountname}
//               onChange={(e) => setAccountName(e.target.value)}
//             />
//             <FaUser className="icon" />
//           </div>
//           <div>
//             <input
//               type="password"
//               placeholder="Password"
//               required
//               value={password}
//               onChange={(e) => setpassword(e.target.value)}
//             />
//             <FaLock className="icon" />
//           </div>

//           <div className="remember-forgot">
//             <label>
//               <input type="checkbox" />
//               Remember me
//             </label>
//             <a href="#">Forgot Password?</a>
//             <button type="submit" className="btn" onClick={handleClick}>
//               Login
//             </button>
//             <div className="resiter-link">
//               <p>
//                 Don't have an account? <Link to="/register">Register</Link>
//               </p>
//             </div>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// };

// export { LoginPage };