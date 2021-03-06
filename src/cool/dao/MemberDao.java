package cool.dao;

import cool.util.DBConnPool;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MemberDao {

    //conn必须关闭，否则达到c3p0连接数上限进程会阻塞
    private Connection conn=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;

    public List<Map<String,Object>> getMemberById(int user_id){
        List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
        conn=DBConnPool.getConnection();
        String sql="select * from member where id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user_id);
            rs= ps.executeQuery();
            if(rs.next()){
                Map<String,Object> d=new HashMap<String,Object>();
                d.put("id",rs.getInt("id"));
                d.put("username",rs.getString("username"));
                d.put("headimg",rs.getInt("headimg"));
                d.put("enrolltime",rs.getDate("headimg"));
                data.add(d);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try{conn.close();} catch (Exception e){}
            }
        }
        return data;
    }

    public void addMember(Map<String,Object> b){
        conn=DBConnPool.getConnection();
        String sql="insert into member(id,username,headimg,enrolltime) values(?,?,?,?)";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,(int)b.get("id"));
            ps.setInt(2,(int)b.get("username"));
            ps.setInt(3,(int)b.get("headimg"));
            ps.setDate(4,new Date(Calendar.getInstance().getTimeInMillis()));
            ps.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try{conn.close();} catch (Exception e){}
            }
        }
    }

}
