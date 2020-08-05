
package data;

import entities.Tema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.sqlite.SQLiteConfig;
import util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class TemaData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(TemaData.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(SQLiteConfig.DEFAULT_DATE_STRING_FORMAT);

    public static int create(Tema d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO tema(asignatura, detalles, fecha_inicio, fecha_vencimiento, date_created) "
                + "VALUES(?,?,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getAsignatura());
            ps.setString(++i, d.getDetalles());
            
            ps.setString(++i, sdf.format(d.getFecha_inicio()));
            
            
            ps.setString(++i, sdf.format(d.getFecha_vencimiento()));
            ps.setString(++i, sdf.format(dt));
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from tema
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(Tema d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE tema SET "
                + "asignatura=?, "
                + "detalles=?, "
                + "fecha_inicio=?, "
                + "fecha_vencimiento=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getAsignatura());
            ps.setString(++i, d.getDetalles());
            
            ps.setString(++i, sdf.format(d.getFecha_inicio()));   
            
            ps.setString(++i, sdf.format(d.getFecha_vencimiento()));

            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM tema WHERE id = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Tema> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Tema> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM tema ORDER BY id";
        } else {
            sql = "SELECT * FROM tema WHERE (id LIKE'" + filter + "%' OR "
                    + "asignatura LIKE'" + filter + "%' OR detalles LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY id";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Tema d = new Tema();
                d.setId(rs.getInt("id"));
                d.setAsignatura(rs.getString("asignatura"));
                d.setDetalles(rs.getString("detalles"));

                String fechae = rs.getString("fecha_inicio");
                
                try {
                    Date date = sdf.parse(fechae);
                    
                 //   System.out.println("Xlist.date:" + date);
                    d.setFecha_inicio(date);
                    
    
                   // System.out.println("list.date_created:" + rs.getString("date_created"));
                } catch (Exception e) {
                }
                
                
                String fecha = rs.getString("fecha_vencimiento");
                try {
                    Date date = sdf.parse(fecha);
                    
                 //   System.out.println("Xlist.date:" + date);
                    d.setFecha_vencimiento(date);
                    
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                   // System.out.println("list.date_created:" + rs.getString("date_created"));
                } catch (Exception e) {
                }

                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static Tema getByPId(int id) {
        Tema d = new Tema();

        String sql = "SELECT * FROM tema WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                d.setAsignatura(rs.getString("asignatura"));
                d.setDetalles(rs.getString("detalles"));

                String fechae = rs.getString("fecha_inicio");
                try {
                    Date date = sdf.parse(fechae);
                    d.setFecha_inicio(date);
                    
                } catch (Exception e) {
                }
                
                
                
                String fecha = rs.getString("fecha_vencimiento");
                try {
                    Date date = sdf.parse(fecha);
                    d.setFecha_vencimiento(date);
                    
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                } catch (Exception e) {
                }
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
}
