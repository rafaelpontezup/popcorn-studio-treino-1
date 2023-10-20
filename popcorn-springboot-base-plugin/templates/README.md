## StackSpot Plugin

## Jinja

You can use jinja to make a template-data folder more dynamic.

complete documentation of jinja: https://jinja.palletsprojects.com/en/3.0.x/templates/

### Example Inputs:
- Resource: {{ resource }}
- Method: {{ method }}

### Inputs

- project_name: {{project_name}}
- project_group_id: {{project_group_id}}
- project_artifact_id: {{project_artifact_id}}
- project_springboot_version: {{project_springboot_version}}
- project_java_version: {{project_java_version}}

### Computed-Inputs

- project_name_capitalized: "{{project_name_capitalized}}"
- project_artifact_id_formatted: "{{project_artifact_id_formatted}}"
- project_artifact_id_sanitized: "{{project_artifact_id_sanitized}}"
- project_base_package: "{{project_base_package}}"
- project_base_package_dir: "{{project_base_package_dir}}"

