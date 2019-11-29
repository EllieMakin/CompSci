#version 140

in vec3 wc_frag_normal;        	// fragment normal in world coordinates (wc_)
in vec2 frag_texcoord;			// texture UV coordinates
in vec3 wc_frag_pos;			// fragment position in world coordinates

out vec3 color;			        // pixel colour

uniform sampler2D tex;  		  // 2D texture sampler
uniform vec3 wc_camera_position;  // Position of the camera in world coordinates


// Tone mapping and display encoding combined
vec3 tonemap(vec3 linearRGB)
{
    float L_white = 0.7; // Controls the brightness of the image

    float inverseGamma = 1./2.2;
    return pow(linearRGB/L_white, vec3(inverseGamma)); // Display encoding - a gamma
}

void main()
{
	vec3 linear_color = vec3(0, 0, 0);
    vec3 light_position = vec3(-4.0, 4.0, -4.0);
    vec4 textureColour = texture(tex, frag_texcoord);
    float I_i = 0.5;
    float I_a = 1;
    float k_d = 0.8;
    float k_s = 1.2;
    vec3 C_diff = vec3(textureColour);
    vec3 C_spec = vec3(1.0, 1.0, 1.0);
    vec3 l_i = normalize(light_position - wc_frag_pos);
    vec3 n = normalize(wc_frag_normal);
    vec3 r_i = normalize(reflect(-l_i, n));
    vec3 v = normalize(wc_camera_position - wc_frag_pos);
    float alpha = 10;

    vec3 ambient = C_diff * I_a;
    vec3 diffuse = C_diff * k_d * I_i * max(0, dot(n, l_i));
    vec3 specular = C_spec * k_s * I_i * pow(max(0, dot(r_i, v)), alpha);

    linear_color += ambient + diffuse + specular;


	color = tonemap(linear_color);
}

