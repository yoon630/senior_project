import mysql from "mysql2";
import async from "hbs/lib/async";

const pool = mysql.createPool({
  host: "localhost",
  port: 3000,
  user: "root",
  password: "0630",
  database: "meditech",
});

const promisPool = pool.promise();

export const loginSql = {
  getMemberId: async () => {
    const [rows] = await promisPool.query(`select id from member_table`);

    return rows;
  },
  getMemberPw: async () => {
    const [rows] = await promisPool.query(
      `select member_password from member_table`
    );
    return rows;
  },
};

export default loginSql;
