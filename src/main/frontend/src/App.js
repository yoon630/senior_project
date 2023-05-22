// Login.js

import React from 'react';
import axios from 'axios';
import logo from "./logo.svg";
import "./App.css";

function App() {
  return (
    <div>
      <header>
        <div className="inner">
          <div className="head-container">
            <div className="head-title">medITech</div>
//            <div className="head-login">
//              <a href="로그인 라우터로 이동">Login</a>
//            </div>
          </div>
        </div>
      </header>
      <div className="login">
        <h1>Login</h1>
        <form action="/" method="post">
          <input name="id" placeholder="Username" required />
          <input type="password" name="memberPassword" placeholder="Password" required />
          <button type="submit" className="btn btn-primary btn-block btn-large"> Let me {'in'}.
          </button>
        </form>
      </div>
    </div>
  );
}

export default App;


//import React, {useEffect, useState} from 'react';
//import axios from 'axios';
//import logo from "./logo.svg";
//import "./App.css";
//
//// 여기가 메인이다.
//function App() {
//  return <div className="App"></div>;
//}
//
//export default App;

//import React, {useEffect, useState} from 'react';
//import axios from 'axios';

//function App() {
//   const [hello, setHello] = useState('')

//    useEffect(() => {
//        axios.get('/api/hello')
//        .then(response => setHello(response.data))
//        .catch(error => console.log(error))
//    }, []);

//    return (
//        <div>
//            백엔드에서 가져온 데이터입니다 : {hello}
//        </div>
//    );
//}

//export default App;
