import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CalculadoraHorasGUI extends JFrame {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private JFormattedTextField txtHoraInicial;
    private JFormattedTextField txtPeriodo;
    private JFormattedTextField txtHoraFinal;
    private JTextArea txtResultado;

    public CalculadoraHorasGUI() {
        setTitle("Calculadora de Horas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            timeFormatter.setPlaceholderCharacter('_');

            JLabel lblHoraInicial = new JLabel("Hora Inicial (HH:mm):");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(lblHoraInicial, gbc);

            txtHoraInicial = new JFormattedTextField(timeFormatter);
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(txtHoraInicial, gbc);

            JLabel lblPeriodo = new JLabel("Período (HH:mm):");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(lblPeriodo, gbc);

            txtPeriodo = new JFormattedTextField(timeFormatter);
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(txtPeriodo, gbc);

            JButton btnSomar = new JButton("Somar Horas");
            btnSomar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    somarHoras();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(btnSomar, gbc);

            JButton btnSubtrair = new JButton("Subtrair Horas");
            btnSubtrair.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    subtrairHoras();
                }
            });
            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(btnSubtrair, gbc);

            JLabel lblHoraFinal = new JLabel("Hora Final (HH:mm):");
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(lblHoraFinal, gbc);

            txtHoraFinal = new JFormattedTextField(timeFormatter);
            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(txtHoraFinal, gbc);

            JButton btnCalcularIntervalo = new JButton("Calcular Intervalo");
            btnCalcularIntervalo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    calcularIntervalo();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            panel.add(btnCalcularIntervalo, gbc);

            txtResultado = new JTextArea(5, 20);
            txtResultado.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(txtResultado);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            panel.add(scrollPane, gbc);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getContentPane().add(panel);
    }

    private void somarHoras() {
        try {
            LocalTime horaInicial = LocalTime.parse(txtHoraInicial.getText(), TIME_FORMATTER);
            Duration duracao = parseDuration(txtPeriodo.getText());
            LocalTime resultado = horaInicial.plus(duracao);
            txtResultado.setText("Resultado: " + resultado.format(TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            txtResultado.setText("Formato de hora inválido. Use HH:mm.");
        }
    }

    private void subtrairHoras() {
        try {
            LocalTime horaInicial = LocalTime.parse(txtHoraInicial.getText(), TIME_FORMATTER);
            Duration duracao = parseDuration(txtPeriodo.getText());
            LocalTime resultado = horaInicial.minus(duracao);
            txtResultado.setText("Resultado: " + resultado.format(TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            txtResultado.setText("Formato de hora inválido. Use HH:mm.");
        }
    }

    private void calcularIntervalo() {
        try {
            LocalTime horaInicial = LocalTime.parse(txtHoraInicial.getText(), TIME_FORMATTER);
            LocalTime horaFinal = LocalTime.parse(txtHoraFinal.getText(), TIME_FORMATTER);
            Duration intervalo = Duration.between(horaInicial, horaFinal);
            long horas = intervalo.toHours();
            long minutos = intervalo.toMinutes() % 60;
            txtResultado.setText("Intervalo: " + horas + " horas e " + minutos + " minutos");
        } catch (DateTimeParseException e) {
            txtResultado.setText("Formato de hora inválido. Use HH:mm.");
        }
    }

    private Duration parseDuration(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return Duration.ofHours(hours).plusMinutes(minutes);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalculadoraHorasGUI().setVisible(true);
            }
        });
    }
}

