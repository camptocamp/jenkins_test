def parse(version) {
  // [2.3.4-5, 2.3.4-5.build6]
  versions = [version, "${version}.build${env.BUILD_NUM}"]

  // [2.3.4, 5]
  tokens = version.tokenize("-")
  upstream_tokens = tokens[0].tokenize(".")

  // [2.3.4-5, 2.3.4-5.build6, 2.3.4, 2.3]
  for (int i=0; i<upstream_tokens.size(); ++i) {
    versions << upstream_tokens.join(".")
    upstream_tokens.pop()
  }
  // Off-by-one errors...
  // [2.3.4-5, 2.3.4-5.build6, 2.3.4, 2.3, 2]
  versions << upstream_tokens.join(".")

  return versions
}

return this;
