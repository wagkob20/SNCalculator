package at.htlkaindorf.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SNCalculatorGUI extends JFrame{
    private JPanel pnContent;
    private JLabel lbHeadline;
    private JTextField tfInput1;
    private JTextField tfInput2;
    private JTextField tfInput4;
    private JTextField tfInput3;
    private JLabel lbIPAddresses;
    private JComboBox cobBits;
    private JLabel lbBits;
    private JButton btCalculate;
    private JTextField tfUsableAddresses;
    private JLabel lbUsableAddresses;
    private JLabel lbNetworkAddresses;
    private JTextField tfNetworkAddresses;
    private JLabel lbBroadcastAddress;
    private JLabel lbFirstHostAddress;
    private JLabel lbLastHostAddress;
    private JTextField tfFirstHostAddress;
    private JTextField tfLastHostAddress;
    private JTextField tfBroadcastAddresses;
    private JLabel lbSNHeadline;
    private JTextField tfSNM1;
    private JTextField tfSNM2;
    private JTextField tfSNM3;
    private JTextField tfSNM4;

    private int[] valuesForLimitation = {0, 8, 16, 24, 32};
    private int[] ipaddressInBinaer = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};
    private int[] ipaddress = {0, 0, 0, 0};
    private int[] subnetMaskInBinaer = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};
    private int[] subnetMask = {0, 0, 0, 0};
    private int[] broadcastAddressInBinaer = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};
    private int[] broadcastAddress = {0, 0, 0, 0};
    private int[] networkAddressInBinaer = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};
    private int[] networkAddress = {0, 0, 0, 0};
    private int[] firstHostAddress = {0, 0, 0, 0};
    private int[] lastHostAddress = {0, 0, 0, 0};

    public SNCalculatorGUI(String title) {
        setContentPane(pnContent);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setTitle(title);

        initComponents();

        pack();

    }

    private void initComponents() {


        btCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcIpAddress();
                calcSNM();
                tfSNM1.setText("" + subnetMask[0]);
                tfSNM2.setText("" + subnetMask[1]);
                tfSNM3.setText("" + subnetMask[2]);
                tfSNM4.setText("" + subnetMask[3]);
                tfUsableAddresses.setText("" + calcAmountUsableAddresses());
                tfBroadcastAddresses.setText(calcBroadcastAddresses());
                tfNetworkAddresses.setText(calcNetworkAddress());
                tfFirstHostAddress.setText(calcFirstHostAddress());
                tfLastHostAddress.setText(calcLastHostAddress());

            }
        });
    }

    private void calcIpAddress() {

        ipaddress[0] = Integer.parseInt(tfInput1.getText());
        ipaddress[1] = Integer.parseInt(tfInput2.getText());
        ipaddress[2] = Integer.parseInt(tfInput3.getText());
        ipaddress[3] = Integer.parseInt(tfInput4.getText());

        for (int i = 0; i < ipaddressInBinaer.length; i++) {
            ipaddressInBinaer[i] = 0;
        }

        for (int i = 0; i < 4; i++) {
            int ipstelle = ipaddress[i];
            int currentValue = 128;
            int j = valuesForLimitation[i];

            while (j < valuesForLimitation[i+1]) {
                if (ipstelle - currentValue >= 0) {
                    ipstelle -= currentValue;
                    ipaddressInBinaer[j] = 1;
                }
                currentValue /= 2;
                ++j;
            }
        }
    }

    private void calcSNM() {

        for (int i = 0; i < subnetMask.length; i++) {
            subnetMask[i] = 0;
        }

        for (int i = 0; i < subnetMaskInBinaer.length; i++) {
            subnetMaskInBinaer[i] = 0;
        }

        String prefixInString = String.valueOf(cobBits.getItemAt(cobBits.getSelectedIndex()));
        int prefix = Integer.parseInt(prefixInString);

        for (int i = 0; i < prefix; i++) {
            subnetMaskInBinaer[i] = 1;
        }

        for (int i = 0; i < 4; i++) {
            int j = valuesForLimitation[i];
            int valueToAdd = 128;

            while (j < valuesForLimitation[i+1]) {
                if (subnetMaskInBinaer[j] == 1) {
                    subnetMask[i] += valueToAdd;
                }
                valueToAdd /= 2;
                j = j + 1;
            }
        }
    }

    private int calcAmountUsableAddresses() {
        String valueInString = String.valueOf(cobBits.getItemAt(cobBits.getSelectedIndex()));
        int value = 32 - Integer.parseInt(valueInString);

        int number = (int) Math.pow(2, value) - 2;

        return number;
    }

    private String calcBroadcastAddresses() {

        for (int i = 0; i < broadcastAddressInBinaer.length; i++) {
            broadcastAddressInBinaer[i] = 0;
        }

        for (int i = 0; i < broadcastAddress.length; i++) {
            broadcastAddress[i] = 0;
        }

        calcIpAddress();

        broadcastAddressInBinaer = ipaddressInBinaer;

        int i = 31;

        while (subnetMaskInBinaer[i] == 0) {
            broadcastAddressInBinaer[i] = 1;
            --i;
        }

        for (i = 0; i < 4; i++) {
            int j = valuesForLimitation[i];
            int valueToAdd = 128;

            while (j < valuesForLimitation[i+1]) {
                if (broadcastAddressInBinaer[j] == 1) {
                    broadcastAddress[i] += valueToAdd;
                }
                valueToAdd /= 2;
                ++j;
            }
        }

        String strReturn = broadcastAddress[0] + "." + broadcastAddress[1] + "."
                               + broadcastAddress[2] + "." + broadcastAddress[3];

        return strReturn;
    }

    private String calcNetworkAddress() {

        for (int i = 0; i < networkAddress.length; i++) {
            networkAddress[i] = 0;
        }

        for (int i = 0; i < networkAddressInBinaer.length; i++) {
            networkAddressInBinaer[i] = 0;
        }

        calcIpAddress();

        int i = 31;

        while (i >= 0) {
            if (ipaddressInBinaer[i] + subnetMaskInBinaer[i] == 2) {
                networkAddressInBinaer[i] = 1;
            }
            --i;
        }

        for (i = 0; i < 4; i++) {
            int j = valuesForLimitation[i];
            int valueToAdd = 128;

            while (j < valuesForLimitation[i+1]) {
                if (networkAddressInBinaer[j] == 1) {
                    networkAddress[i] += valueToAdd;
                }
                valueToAdd /= 2;
                ++j;
            }
        }

        String strReturn = networkAddress[0] + "." + networkAddress[1] + "."
                               + networkAddress[2] + "." + networkAddress[3];

        return strReturn;
    }

    private String calcFirstHostAddress() {

        firstHostAddress = networkAddress;

        firstHostAddress[3] += 1;

        String strReturn = firstHostAddress[0] + "." + firstHostAddress[1] + "."
                               + firstHostAddress[2] + "." + firstHostAddress[3];

        return strReturn;
    }

    private String calcLastHostAddress() {

        lastHostAddress = broadcastAddress;

        lastHostAddress[3] -= 1;

        String strReturn = lastHostAddress[0] + "." + lastHostAddress[1] + "."
                               + lastHostAddress[2] + "." + lastHostAddress[3];

        return strReturn;
    }

    public static void main(String[] args) {
        SNCalculatorGUI gui = new SNCalculatorGUI("SN Calculator");
        gui.setVisible(true);
    }
}
