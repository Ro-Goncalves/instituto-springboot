Método para contar tokens

public int contarTokens(String system, String user) {
    var registry = Encodings.newDefaultEncodingRegistry();
    var enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);
    return enc.countTokens(system + user);
}