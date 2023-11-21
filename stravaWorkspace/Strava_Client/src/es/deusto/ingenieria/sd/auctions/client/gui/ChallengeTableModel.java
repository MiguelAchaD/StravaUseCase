package es.deusto.ingenieria.sd.auctions.client.gui;

import es.deusto.ingenieria.sd.auctions.server.data.dto.ChallengeDTO;

import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ChallengeTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Start Date", "End Date", "Distance", "Duration", "Sport Type", "Progress"};
    private List<ChallengeDTO> challenges;
    
    public ChallengeTableModel(List<ChallengeDTO> challenges) {
        this.challenges = challenges;
    }

    @Override
    public int getRowCount() {
        return challenges.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ChallengeDTO challenge = challenges.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return challenge.getName();
            case 1:
                return challenge.getStartDate();
            case 2:
                return challenge.getEndDate();
            case 3:
                return challenge.getTargetDistance() + " kms";
            case 4:
                return challenge.getTargetTime().toMinutes() + " minutes";
            case 5:
                return challenge.getSportType();
            case 6:
            	if(challenge.getProgress() >= 0) {
            		if(challenge.getProgress() > 1) {
            			return "100%";
            		}else {
            			return challenge.getProgress() * 100 + "%";
            		}
            	}else {
            		return "No Progress";
            	}
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void updateInformation(List<ChallengeDTO> challenges) {
        this.challenges = challenges;
        fireTableDataChanged();
    }

    public ChallengeDTO getChallengeAt(int row) {
        return challenges.get(row);
    }
}

