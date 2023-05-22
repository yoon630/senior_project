import logo from "./logo.svg";
import "./App.css";
import { loginSql } from "./sql";
import express from "express";

const router = express.Router();

// 여기가 메인이다. (login 부분)
function App() {
  return <div className="App"></div>;
}

router.get("/", async function (req, res) {
  const id = await loginSql.getMemberId();
  const password = await loginSql.getMemberPw();

  res.render(`select`, {
    id,
    password,
  });
});

module.exports = router;
export default App;
