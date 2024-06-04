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
        setTitle("Hours Calculator");
        setSize(400, 400); // Aumentei a altura para criar mais espaço
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            timeFormatter.setPlaceholderCharacter('_');

            JLabel lblTitulo = new JLabel("Hours Calculator");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(lblTitulo, gbc);

            // Adicionando um separador entre o título e os outros componentes
            JSeparator separator = new JSeparator();
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 0, 10, 0); // Espaçamento extra
            panel.add(separator, gbc);

            JLabel lblHoraInicial = new JLabel("Hora Inicial (HH:mm):");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.NONE; // Resetando para o padrão
            panel.add(lblHoraInicial, gbc);

            txtHoraInicial = new JFormattedTextField(timeFormatter);
            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(txtHoraInicial, gbc);

            JLabel lblPeriodo = new JLabel("Período (HH:mm):");
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(lblPeriodo, gbc);

            txtPeriodo = new JFormattedTextField(timeFormatter);
            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(txtPeriodo, gbc);

            JButton btnSomar = new JButton("Somar Horas");
            btnSomar.setBackground(new Color(140, 186, 231)); // Tom de azul pastel
            btnSomar.setForeground(Color.WHITE);
            btnSomar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    somarHoras();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(btnSomar, gbc);

            JButton btnSubtrair = new JButton("Subtrair Horas");
            btnSubtrair.setBackground(new Color(140, 186, 231)); // Tom de azul pastel
            btnSubtrair.setForeground(Color.WHITE);
            btnSubtrair.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    subtrairHoras();
                }
            });
            gbc.gridx = 1;
            gbc.gridy = 4;
            panel.add(btnSubtrair, gbc);

            JLabel lblHoraFinal = new JLabel("Hora Final (HH:mm):");
            gbc.gridx = 0;
            gbc.gridy = 5;
            panel.add(lblHoraFinal, gbc);

            txtHoraFinal = new JFormattedTextField(timeFormatter);
            gbc.gridx = 1;
            gbc.gridy = 5;
            panel.add(txtHoraFinal, gbc);

            JButton btnCalcularIntervalo = new JButton("Calcular Intervalo");
            btnCalcularIntervalo.setBackground(new Color(140, 186, 231)); // Tom de azul pastel
            btnCalcularIntervalo.setForeground(Color.WHITE);
            btnCalcularIntervalo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    calcularIntervalo();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            panel.add(btnCalcularIntervalo, gbc);

            txtResultado = new JTextArea(2, 20); // aumentei para 20 linhas
            txtResultado.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(txtResultado);
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 10;
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
