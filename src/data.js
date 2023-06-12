import express from "express";
import mysql from "mysql";

const app = express();
// MySQL 데이터베이스 연결 설정
const connection = mysql.createConnection({
  host: "localhost",
  port: 3306,
  user: "root",
  password: "0630",
  database: "meditech",
});
// MySQL 연결
connection.connect((error) => {
  if (error) {
    console.error("Error connecting to database:", error);
  } else {
    console.log("Connected to database");
  }
});

// 데이터 가져오기 API 엔드포인트
app.get("/test", (req, res) => {
  const query = `SELECT id,epsilon,s1 FROM episode_table`;

  connection.query(query, (error, results) => {
    if (error) {
      console.error("Error executing query:", error);
      res.status(500).json({ error: "An error occurred" });
    } else {
      const data = results.map((row) => ({
        id: row.id,
        epsilon: row.epsilon,
        s1: row.s1,
      }));
      res.json(data);
    }
  });
});
// app.post("/data", async (req, res) => {
//   console.log("Episode Table data post");
//   res.redirect("/data");
// });

// 서버 시작
app.listen(port, () => {
  console.log(`Server is listening on port ${port}`);
});

// ------------------ 여기서 부터 router 만들어서 해보는 방법 -------------------
// const pool = mysql.createPool({
//   host: "localhost",
//   port: 3306,
//   user: "root",
//   password: "0630",
//   database: "meditech",
//   waitForConnections:true,
//   connectionLimit:10,
//   queueLimit:0,
// });

// const selectSql ={
//   getId :async()=> {
//     const [rows]= await promisePool.query(`select id from episode_table `);
//     return rows;
//   },
//   getEpsilon : async()=>{
//     const [rows] = await promisePool.query(`select epsilon from episode_table`);
//     return rows;
//   },
//   getState : async()=>{
//     const [rows] = await promisePool.query(`select s1 from episode_table`);
//     return rows;
//   }
// }
