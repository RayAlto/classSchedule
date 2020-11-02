package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Room;

public class RoomTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public RoomTable() {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("ID");
        tableModel.addColumn("代码");
        tableModel.addColumn("教室名");
        tableColumnModel.getColumn(0).setPreferredWidth(45);
        tableColumnModel.getColumn(1).setPreferredWidth(60);
        tableColumnModel.getColumn(2).setPreferredWidth(100);
    }

    public RoomTable(List<Room> rooms) {
        this();
        for (Room room : rooms) {
            addRow(room);
        }
    }

    public void addRow(Room room) {
        tableModel.addRow(new Object[] { String.valueOf(room.id), room.code, room.roomName });
    }

    public void addRows(List<Room> rooms) {
        for (Room room : rooms) {
            addRow(room);
        }
    }

    public void insertRow(Room room, int index) {
        tableModel.insertRow(index, new Object[] { String.valueOf(room.id), room.code, room.roomName });
    }

    public void removeRow(int index) {
        tableModel.removeRow(index);
    }

    public void removeAll() {
        tableModel.setRowCount(0);
    }

    public int getColumnCount() {
        return tableModel.getColumnCount();
    }

    public String getColumnName(int index) {
        return tableModel.getColumnName(index);
    }

    public int getRowCount() {
        return tableModel.getRowCount();
    }

    public String getValueAt(int row, int column) {
        return (String) tableModel.getValueAt(row, column);
    }

    public void setValueAt(String newValue, int row, int column) {
        tableModel.setValueAt(newValue, row, column);
    }
}