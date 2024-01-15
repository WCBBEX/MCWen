package com.mcen;

import de.kherud.llama.InferenceParameters;
import de.kherud.llama.LlamaModel;
import de.kherud.llama.ModelParameters;
import java.io.IOException;


public class AI {

    LlamaModel model;
    InferenceParameters inferParams;

    private String systemPrompt;
    public AI() {
        ModelParameters modelParams = new ModelParameters();
        this.inferParams = new InferenceParameters()
                .setPenalizeNl(true)
                .setMirostat(InferenceParameters.MiroStat.V2)
                .setAntiPrompt("\n");

        try {
            String modelPath = ResourceExtractor.extract("qwen-model-q2.gguf");
            this.model = new LlamaModel(modelPath,modelParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.systemPrompt =
                "This is a conversation between a user and AI assistant.\n"+
                "AI assistant is eager to help, friendly, honest, good at writing, and always responds immediately and accurately to any request.";

    }

    public AI setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
        return this;
    }


    public class Chat{
        private String context;
        public Chat(){
            this.context = systemPrompt;
        }
        public String getReply(String input){
            StringBuilder reply = new StringBuilder();
            this.context += "\nUser: "+ input + "\nAI assistant: ";
            for (LlamaModel.Output output : model.generate(context, inferParams)) {
                context += output.toString();
                reply.append(output.toString());
            }
            return reply.toString();
        }
    }
}
