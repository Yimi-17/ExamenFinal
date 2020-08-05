package igu.compras.clientes;

import data.TemaData;
import entities.Tema;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import util.Config;

/**
 *
 * @author Asullom
 */
public class TemaTableModel extends AbstractTableModel {

    private List<Tema> lis = new ArrayList();
    private String[] columns = {"#", "Asignatura", "Detalles", "Fecha Inicio", "Fecha Vencimiento"};
    private Class[] columnsType = {Integer.class, String.class, String.class, String.class, String.class};// Date.class
    
    SimpleDateFormat iguSDF = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT_PE);
    

    public TemaTableModel() {
        lis = TemaData.list("");
    }

    public TemaTableModel(String filter) {
        lis = TemaData.list(filter);
    }

    @Override
    public Object getValueAt(int row, int column) {
        Tema d = (Tema) lis.get(row);
        switch (column) {
            case 0:
                return row + 1;
            case 1:
                return d.getAsignatura();
            case 2:
                return d.getDetalles();
            case 3:
                return iguSDF.format(d.getFecha_inicio()); //return (d.getFecha_ven(); 
            case 4:
                return iguSDF.format(d.getFecha_vencimiento()); //return (d.getFecha_ven(); 
            default:
                return null;
        }
    }

    /*
    @Override
    public void setValueAt(Object valor, int row, int column) {
        Tema d = (Tema) lis.get(row);
        switch (column) {
            
           // case 0:
           //     int id = 0;
           //     try {
            //        if (valor instanceof Number) {
           //             id = Integer.parseInt("" + valor);
           //         }
           //     } catch (NumberFormatException nfe) {
            //        System.err.println("" + nfe);
             //   }
            //    d.setId(id);
             //   break;
             
            case 1:
                d.setTema("" + valor);
                break;
            case 2:
                d.setDetalles("" + valor);
                break;

        }
        this.fireTableRowsUpdated(row, row);
        //fireTableCellUpdated(row, row);
    }
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        //Tema c = (Tema) lis.get(row);
        if (column >= 0 && column != 0) {
            //return true;
        }
        return false;//bloquear edicion
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnsType[column];
    }

    @Override
    public int getRowCount() {
        return lis.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public void addRow(Tema d) { // con db no se usa
        this.lis.add(d);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(lis.size(), lis.size());
    }

    public void removeRow(int linha) { // con db no se usa
        this.lis.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public Object getRow(int row) { // usado para paintForm
        this.fireTableRowsUpdated(row, row);
        return lis.get(row);
    }

}
