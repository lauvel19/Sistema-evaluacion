package com.sistema;


import com.sistema.exams.ExamenEscrito;
import com.sistema.exams.ExamenOral;
import com.sistema.exams.ExamenProyecto;
import com.sistema.interfaces.IEvaluador;
import com.sistema.interfaces.IExamen;
import com.sistema.interfaces.IRegistroNotas;
import com.sistema.model.Estudiante;
import com.sistema.model.ResultadoExamen;
import com.sistema.services.EvaluadorGeneral;
import com.sistema.services.RegistroNotas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clase principal con interfaz gráfica.
 * Sistema de Evaluación de Estudiantes - Aplicando SOLID.
 *
 * @author Juan José Quintero, Laura Vélez
 */
public class Main extends JFrame {
    // ✅ CORRECCIÓN: Constantes para literales repetidos
    private static final String TITULO_VENTANA = "Sistema de Evaluación de Estudiantes - SOLID";
    private static final String TITULO_ERROR = "Error";
    private static final String TITULO_EXITO = "Éxito";
    private static final String TITULO_INFO = "Información";
    private static final String MSG_CAMPOS_OBLIGATORIOS = "Todos los campos son obligatorios";
    private static final String MSG_CALIFICACION_INVALIDA = "La calificación debe ser un número válido";
    private static final String MSG_SELECCIONE_RESULTADO = "Seleccione un resultado de la tabla";
    private static final String FUENTE_ARIAL = "Arial"; // ✅ Para evitar duplicación de "Arial"

    // Dependencias (inyección de dependencias - DIP)
    private final transient IEvaluador evaluador;
    private final transient IRegistroNotas registroNotas;

    // Componentes GUI
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JTextField txtCarrera;
    private JTextField txtCalificacion;
    private JComboBox<String> comboTipoExamen;
    private JTextArea txtResultados;
    private DefaultTableModel modeloTabla;
    private JTable tablaResultados;

    public Main() {
        // Inicializar servicios (DIP: dependemos de interfaces)
        this.evaluador = new EvaluadorGeneral();
        this.registroNotas = new RegistroNotas();

        // Configurar ventana
        setTitle(TITULO_VENTANA);
        setSize(900, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // ✅ CORRECCIÓN
        setLocationRelativeTo(null);

        // Crear interfaz
        crearInterfaz();
    }

    private void crearInterfaz() {
        // Panel principal con tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Evaluar Estudiante
        tabbedPane.addTab("📝 Evaluar", crearPanelEvaluacion());

        // Tab 2: Ver Resultados
        tabbedPane.addTab("📊 Resultados", crearPanelResultados());

        // Tab 3: Estadísticas
        tabbedPane.addTab("📈 Estadísticas", crearPanelEstadisticas());

        add(tabbedPane);
    }

    private JPanel crearPanelEvaluacion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Datos del estudiante
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtNombre = new JTextField(20);
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtCodigo = new JTextField(20);
        formPanel.add(txtCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Carrera:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtCarrera = new JTextField(20);
        formPanel.add(txtCarrera, gbc);

        // Tipo de examen
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Tipo de Examen:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        String[] tiposExamen = {"Examen Escrito", "Examen Oral", "Proyecto"};
        comboTipoExamen = new JComboBox<>(tiposExamen);
        formPanel.add(comboTipoExamen, gbc);

        // Calificación
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Calificación (0-100):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        txtCalificacion = new JTextField(20);
        formPanel.add(txtCalificacion, gbc);

        // Botón evaluar
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton btnEvaluar = new JButton("✓ Evaluar Estudiante");
        btnEvaluar.setBackground(new Color(76, 175, 80));
        btnEvaluar.setForeground(Color.WHITE);
        btnEvaluar.setFont(new Font(FUENTE_ARIAL, Font.BOLD, 14)); // ✅ Usar constante
        btnEvaluar.addActionListener(e -> evaluarEstudiante());
        formPanel.add(btnEvaluar, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Área de resultados
        txtResultados = new JTextArea(15, 50);
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado de la Evaluación"));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Crear tabla
        String[] columnas = {"Estudiante", "Código", "Tipo Examen",
                "Calificación", "Estado", "Fecha"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaResultados.getTableHeader().setFont(new Font(FUENTE_ARIAL, Font.BOLD, 12)); // ✅ Usar constante
        JScrollPane scrollTabla = new JScrollPane(tablaResultados);

        panel.add(scrollTabla, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnActualizar = new JButton("🔄 Actualizar Lista");
        btnActualizar.setBackground(new Color(33, 150, 243));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> actualizarTabla());

        JButton btnVerDetalle = new JButton("🔍 Ver Detalle");
        btnVerDetalle.setBackground(new Color(156, 39, 176));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.addActionListener(e -> verDetalleSeleccionado());

        panelBotones.add(btnActualizar);
        panelBotones.add(btnVerDetalle);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea txtEstadisticas = new JTextArea(20, 50);
        txtEstadisticas.setEditable(false);
        txtEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(txtEstadisticas);
        scroll.setBorder(BorderFactory.createTitledBorder("Estadísticas Generales"));

        panel.add(scroll, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("📊 Actualizar Estadísticas");
        btnActualizar.setBackground(new Color(255, 152, 0));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font(FUENTE_ARIAL, Font.BOLD, 13)); // ✅ Usar constante
        btnActualizar.addActionListener(e -> {
            if (registroNotas instanceof RegistroNotas reg) { // ✅ CORRECCIÓN: Pattern matching
                String stats = reg.obtenerEstadisticas();
                txtEstadisticas.setText(stats);
            }
        });

        panel.add(btnActualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void evaluarEstudiante() {
        try {
            // Validar campos
            if (txtNombre.getText().trim().isEmpty() ||
                    txtCodigo.getText().trim().isEmpty() ||
                    txtCarrera.getText().trim().isEmpty() ||
                    txtCalificacion.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        MSG_CAMPOS_OBLIGATORIOS,
                        TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear estudiante
            Estudiante estudiante = new Estudiante(
                    txtNombre.getText().trim(),
                    txtCodigo.getText().trim(),
                    txtCarrera.getText().trim()
            );

            // Obtener calificación
            double calificacion = Double.parseDouble(txtCalificacion.getText().trim());

            // Crear tipo de examen (OCP: fácil agregar nuevos tipos)
            IExamen examen = crearExamen();

            // Evaluar (DIP: usamos interfaces)
            ResultadoExamen resultado = evaluador.evaluarEstudiante(
                    estudiante, examen, calificacion);

            // Registrar resultado
            registroNotas.registrarResultado(resultado);

            // Mostrar resultado
            mostrarResultado(resultado);

            // Limpiar formulario
            limpiarFormulario();

            // Actualizar tabla
            actualizarTabla();

            JOptionPane.showMessageDialog(this,
                    "Evaluación registrada correctamente",
                    TITULO_EXITO, JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    MSG_CALIFICACION_INVALIDA,
                    TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private IExamen crearExamen() {
        // OCP: Fácil agregar nuevos tipos sin modificar código existente
        String tipo = (String) comboTipoExamen.getSelectedItem();

        // ✅ CORRECCIÓN: Usar switch expression moderno
        return switch (tipo != null ? tipo : "") {
            case "Examen Escrito" -> new ExamenEscrito(10, 5);
            case "Examen Oral" -> new ExamenOral("Tema de la materia", false);
            case "Proyecto" -> new ExamenProyecto("Proyecto Final", false, 1);
            default -> new ExamenEscrito(10, 5);
        };
    }

    private void mostrarResultado(ResultadoExamen resultado) {
        // ✅ CORRECCIÓN: Usar Text Block
        String formatoResultado = """
            ╔════════════════════════════════════════════╗
            ║     RESULTADO DE LA EVALUACIÓN             ║
            ╚════════════════════════════════════════════╝
            
            Estudiante: %s
            Código: %s
            Carrera: %s
            
            Tipo de Examen: %s
            Calificación: %.1f/100
            Estado: %s
            
            Observaciones:
            %s
            
            Fecha: %s
            """;

        String texto = String.format(formatoResultado,
                resultado.getEstudiante().getNombre(),
                resultado.getEstudiante().getCodigo(),
                resultado.getEstudiante().getCarrera(),
                resultado.getTipoExamen(),
                resultado.getCalificacion(),
                resultado.getEstado(),
                resultado.getObservaciones(),
                resultado.getFechaFormateada()
        );

        txtResultados.setText(texto);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<ResultadoExamen> resultados = registroNotas.obtenerTodosLosResultados();

        for (ResultadoExamen r : resultados) {
            Object[] fila = {
                    r.getEstudiante().getNombre(),
                    r.getEstudiante().getCodigo(),
                    r.getTipoExamen(),
                    String.format("%.1f", r.getCalificacion()),
                    r.getEstado(),
                    r.getFechaFormateada()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void verDetalleSeleccionado() {
        int fila = tablaResultados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    MSG_SELECCIONE_RESULTADO,
                    TITULO_INFO, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<ResultadoExamen> resultados = registroNotas.obtenerTodosLosResultados();
        if (fila < resultados.size()) {
            ResultadoExamen resultado = resultados.get(fila);

            // ✅ CORRECCIÓN: Usar Text Block
            String detalle = String.format("""
                Estudiante: %s (%s)
                Carrera: %s
                
                Tipo Examen: %s
                Calificación: %.1f
                Estado: %s
                
                Observaciones:
                %s
                
                Fecha: %s""",
                    resultado.getEstudiante().getNombre(),
                    resultado.getEstudiante().getCodigo(),
                    resultado.getEstudiante().getCarrera(),
                    resultado.getTipoExamen(),
                    resultado.getCalificacion(),
                    resultado.getEstado(),
                    resultado.getObservaciones(),
                    resultado.getFechaFormateada()
            );

            JOptionPane.showMessageDialog(this, detalle,
                    "Detalle de Evaluación", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCodigo.setText("");
        txtCarrera.setText("");
        txtCalificacion.setText("");
        comboTipoExamen.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        // Usar el Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Log error but continue with default L&F
            java.util.logging.Logger.getLogger(Main.class.getName())
                    .log(java.util.logging.Level.WARNING, "Cannot set system L&F", e);
        }

        SwingUtilities.invokeLater(() -> {
            Main ventana = new Main();
            ventana.setVisible(true);
        });
    }
}