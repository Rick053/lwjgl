package engine.graph;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram {

    private final int mProgramId;

    private int mVertexShaderId;

    private int mFragmentShaderId;

    private final Map<String, Integer> uniforms;

    public ShaderProgram() throws Exception {
        mProgramId = glCreateProgram();
        if (mProgramId == 0) {
            throw new Exception("Could not create Shader");
        }
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(mProgramId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Dump the matrix into a float buffer
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public void createVertexShader(String shaderCode) throws Exception {
        mVertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        mFragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(mProgramId, shaderId);

        return shaderId;
    }

    public void link() throws Exception {
        glLinkProgram(mProgramId);
        if (glGetProgrami(mProgramId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(mProgramId, 1024));
        }

        if (mVertexShaderId != 0) {
            glDetachShader(mProgramId, mVertexShaderId);
        }
        if (mFragmentShaderId != 0) {
            glDetachShader(mProgramId, mFragmentShaderId);
        }

        glValidateProgram(mProgramId);
        if (glGetProgrami(mProgramId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(mProgramId, 1024));
        }
    }

    public void bind() {
        glUseProgram(mProgramId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (mProgramId != 0) {
            glDeleteProgram(mProgramId);
        }
    }
}
