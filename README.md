# Radioactive Decay Volume Calculator / Calculadora de Decaimiento Radiactivo

[🇪🇸 Leer en Español](#español) | [🇬🇧 Read in English](#english)

---

<a name="english"></a>
## 🇬🇧 English

A Java-based, cross-platform desktop application (Swing) designed for nuclear medicine departments and radiopharmacies. It precisely calculates the required syringe volume for a target radioactive dose over time, automatically adjusting for the physical decay of the isotope (e.g., 99mTc).

### Clinical Features
* **Time-Zero Volume ($V_0$):** Calculates the exact ideal volume to inject at the moment of preparation.
* **Injection Timetable:** Generates a precise, minute-by-minute wait-time table mapped to standard 0.1 ml syringe increments to maintain a constant target dose.
* **Native Multilingual Support:** The user interface can be dynamically switched between **English, Español, Français, Português, 中文 (Chinese), and 日本語 (Japanese)**.
* **Clinical PDF Export:** Automatically generates an A4 PDF report detailing the input parameters, institution name, and the full injection timetable using iText.

### Tech Stack
* **Language:** Java 21
* **UI Framework:** Java Swing
* **Build Tool:** Apache Maven
* **PDF Generation:** iText 5.5.13

### License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<a name="español"></a>
## 🇪🇸 Español

Aplicación de escritorio multiplataforma desarrollada en Java (Swing) orientada a servicios de medicina nuclear y radiofarmacia. Permite calcular con precisión los volúmenes de inyección incrementales necesarios para administrar una dosis objetivo constante, compensando el decaimiento físico del isótopo (ej. 99mTc) a lo largo del tiempo.

### Características Clínicas
* **Cálculo de Volumen Cero ($V_0$):** Determina el volumen ideal a inyectar en el momento exacto de la preparación.
* **Cronograma Escalonado:** Genera una tabla de tiempos de espera precisos (minutos) asociados a incrementos de 0.1 ml en la jeringa para mantener la dosis requerida.
* **Soporte Multilingüe Nativo:** La interfaz de usuario puede alternarse dinámicamente entre **Inglés, Español, Francés, Portugués, Chino y Japonés**.
* **Exportación Clínica a PDF:** Generación automatizada de reportes en PDF formato A4 listos para imprimir, incluyendo datos de la institución, parámetros de entrada y la tabla de inyección mediante iText.

### Tecnologías
* **Lenguaje:** Java 21
* **Interfaz Visual:** Java Swing
* **Gestor de dependencias:** Apache Maven
* **Generación de PDF:** iText 5.5.13

### Licencia
Este proyecto está bajo la Licencia MIT - mira el archivo [LICENSE](LICENSE) para más detalles.